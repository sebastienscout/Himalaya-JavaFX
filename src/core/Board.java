package core;

import java.util.ArrayList;

public class Board {
    
    private ArrayList<Player> players;
    private ArrayList<Village> villages;
    private int nbTurn;
    private BagResources bagResources;
    private BagOrders bagOrders;

    public Board() {
        
        nbTurn = 1;
        players = new ArrayList<>();
        villages = new ArrayList<>();
        bagResources = new BagResources();
        bagOrders = new BagOrders();
        
        System.out.println("Ca m'a l'air pas mal.");
        
    }
    
}
