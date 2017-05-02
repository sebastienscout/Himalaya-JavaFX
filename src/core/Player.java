package core;

import java.util.ArrayList;

public class Player {

    private String color;
    private int politicalScore;
    private int religiousScore;
    private int economicScore;
    private ArrayList<Resource> resources;
    private ArrayList<Action> actions;

    public Player(String color) {
        this.color = color;
        politicalScore = 0;
        religiousScore = 0;
        economicScore = 0;
        resources = new ArrayList<>();
        actions = new ArrayList<>();
    }

    public void addResources(Resource resource) {
        this.resources.add(resource);
    }

    public void addActions(Action action) {
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

}
