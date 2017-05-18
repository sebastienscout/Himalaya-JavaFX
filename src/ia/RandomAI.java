package ia;

import core.Action;
import core.Player;
import core.Village;

public class RandomAI extends Player {
    
    public RandomAI(String color, Village village){
        super(color, village);
    }
    
    public Action getRandomAction(){
        int randAction = (int)(Math.random()* 7 );
        Action action = null;
        switch(randAction){
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
                action = new Action(Action.Type.delegation, getPosition().getRegions().get(0).getId());
                break;
        }
        
        return action;
    }
}
