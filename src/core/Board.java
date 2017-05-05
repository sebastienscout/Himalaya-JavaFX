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
    
    public Village getVillageById(int id) {
        
        Village result = null;
        
        for (Village village : villages) {
            if (village.getId() == id) {
                result = village;
            }
        }
        
        return result;
    }
    
    public Region getRegionById(int id) {
        
        Region result = null;
        
        for (Region region : regions) {
            if (region.getId() == id) {
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
    
    public void addPlayer(Player p) {
        players.add(p);
    }
    
    public void addVillage(Village v) {
        villages.add(v);
    }
    
    public void addRegion(Region r) {
        regions.add(r);
    }
    
    public void transaction(Player p) {
        System.out.println("Transaction");
        Village village = p.getPosition();
        int value = 0;
        Resource resSelected = null;

        //Pour faire une Commande
        if (village.getResources().isEmpty() && village.getOrder() != null) {
            
            ArrayList<Resource> resourceNew = new ArrayList<>();
            ArrayList<Resource> resourcesCopy = new ArrayList<>();
            int nbResourcesInOrder = 0;
            int counterForPlayer = 0;
            
            nbResourcesInOrder = village.getOrder().getResources().size();
            
            for (Resource res : p.getResources()) {
                resourcesCopy.add(res);
            }
            
            for (Resource res : village.getOrder().getResources()) {
                int k = 0;
                boolean resourceGiven = false;
                while (k < resourcesCopy.size() && resourceGiven == false) {
                    if (resourcesCopy.get(k).getType().equals(res.getType())) {
                        resourcesCopy.remove(res);
                        resourceNew.add(res);
                        counterForPlayer++;
                        resourceGiven = true;
                        System.out.println("nbCounter = " + counterForPlayer + "/" + nbResourcesInOrder);
                    }
                    k++;
                }
            }

            //Si le joueur a bien honoré sa commande
            if (counterForPlayer == nbResourcesInOrder) {
                //Pour chaque resource on l'enlève au joueur et rajoute dans le sac 
                for (Resource res : resourceNew) {
                    p.getResources().remove(res);
                    this.getBagResources().addResource(res);
                }
                village.removeOrder();
            }
        } //Pour prendre resources du village
        else if (village.getResources().size() > 0 && village.getOrder() == null) {
            value = village.getResources().get(0).getValue();
            for (Resource villResource : village.getResources()) {
                if (villResource.getValue() <= value) {
                    value = villResource.getValue();
                    resSelected = villResource;
                }
            }
            if (resSelected != null) {
                p.addResource(resSelected);
                village.removeResource(resSelected);
            }
        }
    }
    
    public void executeActions() {
        for (int i = 0; i < 6; i++) {
            for (Player p : players) {
                switch (p.getAction(i)) {
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
                        if (p.getPosition().getStupa() == null) {
                            p.putStupa();
                        } else {
                            System.out.println("Il y a déjà une stupa sur ce village.");
                        }
                        break;
                    case transaction:
                        transaction(p);
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
