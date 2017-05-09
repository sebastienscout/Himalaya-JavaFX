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
    private int nbTransactionDone;
    private boolean completedOrder;
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
        completedOrder = false;
        nbTransactionDone = 0;
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

    public int getNbTransactionDone() {
        return nbTransactionDone;
    }

    public void setNbTransactionDone(int nbTransactionDone) {
        this.nbTransactionDone = nbTransactionDone;
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

    public boolean asCompletedOrder() {
        return completedOrder;
    }

    public void setCompletedOrder(boolean completedOrder) {
        this.completedOrder = completedOrder;
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

    public ArrayList<Resource> getResources() {
        return resources;
    }
    
    public void removeResource(Resource r){
        resources.remove(r);
    }
    
    public Resource getSpecificResource(Resource.Type type){
        for (Resource res : resources) {
          if(res.getType().equals(type)){
              return res;
          }
        }
        return null;    
    }
}
