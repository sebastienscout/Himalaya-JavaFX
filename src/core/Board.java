package core;

import java.util.ArrayList;

public class Board {

    private ArrayList<Player> players;
    private ArrayList<Village> villages;
    private ArrayList<Region> regions;
    private BagResources bagResources;
    private BagOrders bagOrders;
    private int nbTurn;
    private int currentPlayer;

    public Board() {
        nbTurn = 1;
        currentPlayer = 0;
        players = new ArrayList<>();
        villages = new ArrayList<>();
        regions = new ArrayList<>();
        bagResources = new BagResources();
        bagOrders = new BagOrders();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Village> getVillages() {
        return villages;
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public Village getVillageById(int id){

        Village result = null;

        for (Village village : villages) {
            if(village.getId() == id){
                result = village;
            }
        }

        return result;
    }

    public Region getRegionById(int id){

        Region result = null;

        for (Region region : regions) {
            if(region.getId() == id){
                result = region;
            }
        }

        return result;
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

    public void addPlayer(Player p){
        players.add(p);
    }

    public void addVillage(Village v) {
        villages.add(v);
    }

    public void addRegion(Region r) {
        regions.add(r);
    }

    public void executeActions() {
        for (int i = 0; i < 6; i++) {
            for(Player p : players){
                switch(p.getAction(i)){
                    case stone:
                        System.out.println("Déplacement chemin de pierre");
                        p.move(p.getPosition().getDestVillage(Road.stone));
                        break;
                    case soil:
                        System.out.println("Déplacement chemin de terre");
                        p.move(p.getPosition().getDestVillage(Road.soil));
                        break;
                    case ice:
                        System.out.println("Déplacement chemin de glace");
                        p.move(p.getPosition().getDestVillage(Road.ice));
                        break;
                    case offering:
                        System.out.println("Offrande");
                        break;
                    case transaction:
                        System.out.println("Transaction");
                        break;
                    case pause:
                        System.out.println("Pause");
                        break;
                    case delegation:
                        System.out.println("Délégation");
                        break;
                }
            }
        }
    }

}
