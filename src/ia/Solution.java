package ia;

import core.Action;
import core.Board;
import core.Player;
import core.Region;
import core.Resource;
import core.Village;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Solution {

    private double fitness;
    private ArrayList<Action> actions;
    private int size = 6;
    private Player p;
    private Board cloneBoard;
    private Player clonePlayer;

    private final int malus = 100;
    private final int weightStupa = 20;

    public Solution() {
        this.actions = null;
        this.fitness = 0;
        actions = new ArrayList<>(size);
    }

    public Solution(Solution solution) {
        this.fitness = solution.fitness;
        this.size = solution.size;
        this.p = solution.p;
        this.actions = new ArrayList<>();
        for (Action action : solution.actions) {
            this.actions.add(new Action(action.getType(), action.getId()));
        }
    }

    public void computeFitness(Board b) {

        cloneBoard = new Board(b);
        clonePlayer = new Player(p);
        
        clonePlayer.setPosition(cloneBoard.getVillageById(p.getPosition().getId()));
        
        cloneBoard.addPlayer(clonePlayer);

        for (Action action : actions) {
            clonePlayer.addAction(action);
        }

        // Actions
        for (int i = 0; i < 6; i++) {
            Action action = clonePlayer.getAction(i);
            testAction(action);
            if (action.getType() == Action.Type.delegation) {
                action.setId(computeOptimalRegion(b));
            }
            cloneBoard.executeAction(i, clonePlayer, false);
        }

        clonePlayer.clearEndOfTurn();

        // Compute fitness
        fitness = computeResourcesFitness();
        fitness += calculWeightStupas(b) * weightStupa * (p.getNbStupa() - clonePlayer.getNbStupa());
        fitness += (p.getNbDelegation() - clonePlayer.getNbDelegation()) * computeWeightDelegation(b);
        fitness += (clonePlayer.getEconomicScore() - p.getEconomicScore()) * computeWeightBartering(b);

        //Malus
        int nbTrans = clonePlayer.getNbTransactionDone();
        if (clonePlayer.hasCompletedOrder()) {
            fitness -= (2 - nbTrans) * malus;
        }
    }

    public void testAction(Action action) {

        switch (action.getType()) {
            case transaction:
                if (clonePlayer.getPosition().getResources().isEmpty() && clonePlayer.getPosition().getOrder() == null) {
                    action.setType(Action.Type.pause);
                }
                break;
            case bartering:
                if (!clonePlayer.canBartering()) {
                    action.setType(Action.Type.pause);
                }

                break;
            case offering:
                if (!clonePlayer.canPutStupa()) {
                    action.setType(Action.Type.pause);
                }
                break;

            case delegation:
                if (!clonePlayer.canPutDelegation()) {
                    action.setType(Action.Type.pause);
                }
                break;
        }
    }

    public double getFitness() {
        return fitness;
    }

    public void init(Player p) {
        this.p = p;

        for (int i = 0; i < size; i++) {
            int randAction = (int) (Math.random() * 7);
            Action action = null;
            switch (randAction) {
                case 1:
                    action = new Action(Action.Type.stone);
                    break;
                case 2:
                    action = new Action(Action.Type.ice);
                    break;
                case 3:
                    action = new Action(Action.Type.soil);
                    break;
                case 4:
                    action = new Action(Action.Type.transaction);
                    break;
                case 5:
                    action = new Action(Action.Type.bartering);
                    break;
                case 6:
                    action = new Action(Action.Type.offering);
                    break;
                case 7:
                    action = new Action(Action.Type.pause);
                    break;
                case 0:
                    action = new Action(Action.Type.delegation, p.getPosition().getRegions().get(0));
                    break;
            }
            actions.add(action);
        }
    }

    public int size() {
        return size;
    }

    public Action get(int i) {
        return actions.get(i);
    }

    public void set(int i, Action action) {
        actions.set(i, action);
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void mutate(int i) {
        Action tmp = actions.get(i);
        Action newAction = tmp;
        while (newAction.getType().equals(tmp.getType())) {
            int randAction = (int) (Math.random() * 7);
            Action action = null;
            switch (randAction) {
                case 1:
                    action = new Action(Action.Type.stone);
                    break;
                case 2:
                    action = new Action(Action.Type.ice);
                    break;
                case 3:
                    action = new Action(Action.Type.soil);
                    break;
                case 4:
                    action = new Action(Action.Type.transaction);
                    break;
                case 5:
                    action = new Action(Action.Type.bartering);
                    break;
                case 6:
                    action = new Action(Action.Type.offering);
                    break;
                case 7:
                    action = new Action(Action.Type.pause);
                    break;
                case 0:
                    action = new Action(Action.Type.delegation, 1);
                    break;
            }
            newAction = action;
        }
        actions.set(i, newAction);
    }

    public double calculWeightStupas(Board b) {

        double coeff = 1.0;

        if (b.winnerReligiousScore() != null) {
            if (p.equals(b.winnerReligiousScore())) {
                coeff = 0.5;
            } else {
                coeff = b.winnerReligiousScore().getReligiousScore() - p.getReligiousScore();
            }
        }

        return coeff;
    }

    public double computeWeightBartering(Board b) {
        double coeff = 1.0;

        if (b.winnerEconnomicScore() != null) {
            if (p.equals(b.winnerReligiousScore())) {
                coeff = 0.5;
            } else {
                coeff = b.winnerEconnomicScore().getEconomicScore() - p.getEconomicScore();
            }
        }

        return coeff;
    }

    public double computeWeightDelegation(Board b) {
        double coeff = 1.0;

        if (b.winnerPoliticalScore() != null) {
            if (p.equals(b.winnerPoliticalScore())) {
                coeff = 0.5;
            } else {
                coeff = b.winnerPoliticalScore().getPoliticalScore() - p.getPoliticalScore();
            }
        }

        return coeff;
    }

    /**
     * Permet de déterminer où le joueur va placer les délégations
     *
     * @param b Board
     */
    public int computeOptimalRegion(Board b) {
        HashMap<Integer, Integer> score = new HashMap<>();

        for (Integer idRegion : clonePlayer.getPosition().getRegions()) {

            Region region = cloneBoard.getRegionById(idRegion);
            String playerMax = region.getMaxDelegationPlayer();

            if (!clonePlayer.getColor().equals(playerMax)) {
                int nbDelegationsPlayerMax = 0;
                int nbDelegations = 0;
                int nbDelegationsAvailable = 1;

                Village.Type type = clonePlayer.getPosition().getType();
                if (type.equals(Village.Type.temple)) {
                    nbDelegationsAvailable = 2;
                } else if (type.equals(Village.Type.monastery)) {
                    nbDelegationsAvailable = 3;
                }

                if (playerMax != null) {
                    nbDelegationsPlayerMax = b.getPlayerByColor(playerMax).getDelegations().get(idRegion);
                }
                if (clonePlayer.getDelegations().size() > 0 && clonePlayer.getDelegations().get(idRegion) != null) {
                    nbDelegations = clonePlayer.getDelegations().get(idRegion);
                }

                score.put(idRegion, nbDelegationsAvailable + nbDelegations - nbDelegationsPlayerMax);

            }
        }

        if (score.size() > 0) {
            return Collections.max(score.entrySet(), Map.Entry.comparingByValue()).getKey();
        }

        return clonePlayer.getPosition().getRegions().get(0);

    }

    public int computeResourcesFitness() {
        int totalValueResourcesClone = 0;
        int totalValueResourcesAI = 0;

        for (Resource resource : clonePlayer.getResources()) {
            totalValueResourcesClone += resource.getValue();
        }

        for (Resource resource : p.getResources()) {
            totalValueResourcesAI += resource.getValue();
        }

        return totalValueResourcesClone - totalValueResourcesAI;
    }

    @Override
    public String toString() {
        return "Solution{" + "fitness=" + fitness + ", actions=" + actions + '}';
    }
}
