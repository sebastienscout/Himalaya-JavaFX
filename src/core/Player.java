package core;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {

    private String color;
    private int politicalScore;
    private int religiousScore;
    private int economicScore;
    private int nbStupa;
    private int nbDelegation;
    private Village currentPosition;
    private ArrayList<Resource> resources;
    private ArrayList<Action> actions;
    private HashMap<Region, Integer> delegations;

    public Player(String color, Village v) {
        this.color = color;
        currentPosition = v;
        politicalScore = 0;
        religiousScore = 0;
        economicScore = 0;
        nbStupa = 5;
        nbDelegation = 15;
        resources = new ArrayList<>();
        actions = new ArrayList<>();
        delegations = new HashMap<>();
    }

    public HashMap<Region, Integer> getDelegations() {
        return delegations;
    }

    public void addDelegations(Region r, Integer nb) {
        nbDelegation -= nb;
        delegations.put(r, nb);
    }
    
    public void putStupa(){
        if(nbStupa > 0 && currentPosition.getStupa() == null){
            nbStupa--;
            currentPosition.setStupa(this);
        }
        else {
            System.out.println("Vous n'avez plus de Stupa.");
        }
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public int getPoliticalScore() {
        return politicalScore;
    }

    public int getReligiousScore() {
        return religiousScore;
    }

    public int getEconomicScore() {
        return economicScore;
    }

    public String getColor() {
        return color;
    }
    
    public Village getPosition(){
        return currentPosition;
    }
    
    public void resetActions(){
        actions.clear();
    }
    
    public Action getAction(int i){
        return actions.get(i);
    }
    
    public void move(Village dest){
        currentPosition = dest;
    }

}
