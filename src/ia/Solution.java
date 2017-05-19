package ia;

import core.Action;
import core.Player;
import java.util.ArrayList;

public class Solution {

    private double fitness;
    private ArrayList<Action> actions;
    private int size = 6;
    private Player p;

    public Solution() {
        this.actions = null;
        this.fitness = 0;
        actions = new ArrayList<>(size);
    }

    @Override
    public String toString() {
        return "Solution{" + "fitness=" + fitness + ", actions=" + actions + '}';
    }

    public void calculateFitness() {
        // TODO
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
                    action = new Action(Action.Type.delegation, p.getPosition().getRegions().get(0).getId());
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
        while(newAction.getType().equals(tmp.getType())){
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
}
