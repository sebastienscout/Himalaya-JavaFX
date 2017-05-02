package core;

import java.util.ArrayList;

public class Player {

    private String color;
    private int politicalScore;
    private int religiousScore;
    private int economicScore;
    private Village currentPosition;
    private ArrayList<Resource> resources;
    private ArrayList<Action> actions;

    public Player(String color, Village v) {
        this.color = color;
        currentPosition = v;
        politicalScore = 0;
        religiousScore = 0;
        economicScore = 0;
        resources = new ArrayList<>();
        actions = new ArrayList<>();
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
