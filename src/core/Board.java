package core;

import java.util.ArrayList;

public class Board {
    
    private ArrayList<Player> players;
    private ArrayList<Village> villages;
    private BagResources bagResources;
    private BagOrders bagOrders;
    private int nbTurn;
    private int currentPlayer;

    public Board() {
        
        nbTurn = 1;
        currentPlayer = 0;
        players = new ArrayList<>();
        villages = new ArrayList<>();
        bagResources = new BagResources();
        bagOrders = new BagOrders();
        
        players.add(new Player("rouge"));
        players.add(new Player("bleu"));
        players.add(new Player("jaune"));
        players.add(new Player("vert"));
        
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Village> getVillages() {
        return villages;
    }

    public int getNbTurn() {
        return nbTurn;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public BagResources getBagResources() {
        return bagResources;
    }

    public BagOrders getBagOrders() {
        return bagOrders;
    }
       
}
