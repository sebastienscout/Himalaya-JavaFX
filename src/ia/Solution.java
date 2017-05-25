package ia;

import core.Action;
import core.Board;
import core.Player;
import core.Resource;
import java.util.ArrayList;
import java.util.Map;

public class Solution {

    private double fitness;
    private ArrayList<Action> actions;
    private int size = 6;
    private Player p;
    private Player strongest;
    private Player weakest;
    private final int weightStupa = 20;
    private int coeffDelegation = 10;
    private Board cloneBoard;
    private Player clonePlayer;

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

    public void calculateFitness(Board b) {

        cloneBoard = new Board(b);
        clonePlayer = new Player(p);
        clonePlayer.move(cloneBoard.getVillageById(p.getPosition().getId()));
        cloneBoard.addPlayer(clonePlayer);

        for (Action action : actions) {
            clonePlayer.addAction(action);
        }

        clonePlayer.setCompletedOrder(false, 0, 0);
        clonePlayer.setNbTransactionDone(0);

        // Actions
        for (int i = 0; i < 6; i++) {
            Action action = clonePlayer.getAction(i);
            if (action.getType() == Action.Type.delegation) {
                action.setId(clonePlayer.getPosition().getRegions().get(0));
            }
            cloneBoard.executeAction(i, clonePlayer);
            //System.out.println("Position : " + clonePlayer.getPosition().getId() + " (" + clonePlayer.getPosition() + ") > Resources : " + clonePlayer.getPosition().getResources());
        }

        clonePlayer.clearActions();

        int totalValueResourcesClone = 0;
        int totalValueResourcesAI = 0;

        for (Resource resource : clonePlayer.getResources()) {
            totalValueResourcesClone += resource.getValue();
        }

        for (Resource resource : p.getResources()) {
            totalValueResourcesAI += resource.getValue();
        }

        fitness = totalValueResourcesClone - totalValueResourcesAI;
        //Calcul Fitness Stupa
        fitness += calculStupas(b) * weightStupa * (p.getNbStupa() - clonePlayer.getNbStupa());
        System.out.println(p.getColor() + " score : " + p.getReligiousScore() + " => poids religieux = " + calculStupas(b) * weightStupa);
        
        fitness += (p.getNbDelegation() - clonePlayer.getNbDelegation()) * coeffDelegation;
        //System.out.println("Fitness : " + fitness + ", actions = " + actions + ", resources = " + clonePlayer.getResources() + "COEFF : " + weightStupa);
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
                    action = new Action(Action.Type.delegation, 5);
                    break;
            }
            newAction = action;
        }
        actions.set(i, newAction);
    }

    public double calculStupas(Board b) {

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

    /**
     * Calcul la fitness pour les délégations, par rapport aux régions où le
     * joueur dommine ...
     *
     * @param clonePlayer Player cloné
     */
    public void calculFitnessDelegation(Player clonePlayer) {
        int totalNbDelegationAi = 0;
        int totalNbDelegationClone = 0;
        //Ajout prise en compte de la prise de commande
        for (Map.Entry<Integer, Integer> en : p.getDelegations().entrySet()) {
            Integer region = en.getKey();
            Integer nbDelegation = en.getValue();
            totalNbDelegationAi += nbDelegation;
        }
        for (Map.Entry<Integer, Integer> en : clonePlayer.getDelegations().entrySet()) {
            Integer region = en.getKey();
            Integer nbDelegation = en.getValue();
            totalNbDelegationClone += nbDelegation;
        }
        int scoreDelegation = 0;
        scoreDelegation = totalNbDelegationClone - totalNbDelegationAi;
        fitness += scoreDelegation;
        System.out.println("Delegation ia = " + totalNbDelegationAi + " Fitness new " + fitness);

    }

    @Override
    public String toString() {
        return "Solution{" + "fitness=" + fitness + ", actions=" + actions + '}';
    }
}
