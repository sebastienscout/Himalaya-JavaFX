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
        bagOrders = new BagOrders(40);
    }
    
    /**
     * Constructeur par copie
     * @param board 
     */
    public Board(Board board){
        this.currentPlayer = board.currentPlayer;
        this.nbTurn = board.nbTurn;
        this.players = new ArrayList<>();
        this.villages = new ArrayList<>();
        this.regions = new ArrayList<>();
        
        for (Village village : board.villages) {
            this.villages.add(new Village(village));
        }
        for (Region region : board.regions) {
            this.regions.add(new Region(region));
        }
        
        this.bagOrders = new BagOrders(board.bagOrders);
        this.bagResources = new BagResources(board.bagResources);
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

    /**
     * Effectue une transaction => 
     *  - Si il y a des ressources sur le village on récupère la ressource la 
     * + faible 
     *  - Si il y a une commande on donne nos ressources pour honorer la 
     * commande
     * @param p Joueur
     */
    private void transaction(Player p) {
        Village village = p.getPosition();

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
                    }
                    k++;
                }
            }

            //Si le joueur a bien honoré sa commande
            if (counterForPlayer == nbResourcesInOrder) {
                //Pour chaque resource on l'enlève au joueur et rajoute dans le sac 
                for (Resource res : resourceNew) {
                    p.removeResource(p.getSpecificResource(res.getType()));
                    this.getBagResources().addResource(res);
                }

                p.setCompletedOrder(true, village.getId(), village.getOrder().getValue());
                getBagOrders().getOrders().add(village.getOrder());
                village.removeOrder();
            }
        } //Pour prendre resources du village
        else if (village.getResources().size() > 0 && village.getOrder() == null && p.canTakeResource()) {
            Resource resSelected = null;
            int value = village.getResources().get(0).getValue();
            for (Resource villResource : village.getResources()) {
                if (villResource.getValue() <= value) {
                    value = villResource.getValue();
                    resSelected = villResource;
                }
            }
            if (resSelected != null) {
                p.addResource(resSelected);
                p.addResourceTaken(village);
                village.removeResource(resSelected);
            }
        }
    }

    /**
     * Ajoute une ou plusieurs délégations sur une région voisine du village
     * @param p
     * @param action 
     */
    private void delegation(Player p, Action action) {
        // Verification commande completée, et moins de 2 actions
        if (p.asCompletedOrder() && p.getNbTransactionDone() < 2) {
            if (p.getPosition().getId() == p.getVillageOrderId()) {
                p.setNbTransactionDone(p.getNbTransactionDone() + 1);

                // Id de la region contenu dans l'action delegation
                Region choice = getRegionById(action.getId());
                Integer nbDeleg = 0;

                // Type du village => nombre de delegation dispo
                switch (p.getPosition().getType()) {
                    case house:
                        nbDeleg = 1;
                        break;
                    case temple:
                        nbDeleg = 2;
                        break;
                    case monastery:
                        nbDeleg = 3;
                        break;
                }

                // Ajout des delegations
                choice.addDelegations(p, nbDeleg);
                p.addDelegations(choice, nbDeleg);

            } else {
//                System.out.println("Troc : Vous devez être sur le village où vous avez répondu à la commande.");
            }
        } else {
//            System.out.println("Delegation : Vous devez avoir complété une commande, et moins de 2 transactions.");
        }

    }

    /**
     * Effectue une offrande sur le village
     * @param p 
     */
    private void offering(Player p) {
        // Verification commande completée, et moins de 2 actions
        if (p.asCompletedOrder() && p.getNbTransactionDone() < 2) {

            if (p.getPosition().getId() == p.getVillageOrderId()) {
                p.setNbTransactionDone(p.getNbTransactionDone() + 1);

                // Verification : pas de stupa deja placée
                if (p.getPosition().getStupa() == null) {
                    p.putStupa();
                    int nbPoints = 0;
                    switch (p.getPosition().getType()) {
                        case house:
                            nbPoints = 1;
                            break;
                        case temple:
                            nbPoints = 2;
                            break;
                        case monastery:
                            nbPoints = 3;
                            break;
                    }
                    p.setReligiousScore(p.getReligiousScore() + nbPoints);
                } else {
//                    System.out.println("Offrande : Il y a déjà une stupa sur ce village.");
                }
            } else {
//                System.out.println("Troc : Vous devez être sur le village où vous avez répondu à la commande.");
            }

        } else {
//            System.out.println("Offrande : Vous devez avoir complété une commande, et moins de 2 transactions.");
        }
    }

    /**
     * Effectue le troc pour récupèrer yack sur commande (point éco)
     * @param p Player
     */
    public void bartering(Player p) {
        if (p.asCompletedOrder() && p.getNbTransactionDone() < 2) {
            if (p.getPosition().getId() == p.getVillageOrderId()) {
                p.setNbTransactionDone(p.getNbTransactionDone() + 1);
                p.setEconomicScore(p.getEconomicScore() + p.getNbYacksOrder());
            } else {
//                System.out.println("Troc : Vous devez être sur le village où vous avez répondu à la commande.");
            }
        } else {
//            System.out.println("Troc : Vous devez avoir complété une commande, et moins de 2 transactions.");
        }

    }

    public void prepareActions() {
        for (Player p : players) {
            p.setCompletedOrder(false, 0, 0);
            p.setNbTransactionDone(0);
        }
    }

    /**
     * Exécution des actions après plannification d'un joueur en particulier
     * @param i numéro de l'action dans l'ordre de la plannification
     * @param p Player
     */
    public void executeAction(int i, Player p) {
        switch (p.getAction(i).getType()) {
            case stone:
                if (p.getPosition().getDestVillage(Road.stone) != null) {
                    p.move(getVillageById(p.getPosition().getDestVillage(Road.stone)));
                } else {
//                    System.out.println("Erreur ! Il n'a pas de route <stone> pour le village " + p.getPosition().getId());
                }
                break;
            case soil:
                if (p.getPosition().getDestVillage(Road.soil) != null) {
                    p.move(getVillageById(p.getPosition().getDestVillage(Road.soil)));
                } else {
//                    System.out.println("Erreur ! Il n'a pas de route <soil> pour le village " + p.getPosition().getId());
                }
                break;
            case ice:
                if (p.getPosition().getDestVillage(Road.ice) != null) {
                    p.move(getVillageById(p.getPosition().getDestVillage(Road.ice)));
                } else {
//                    System.out.println("Erreur ! Il n'a pas de route <ice> pour le village " + p.getPosition().getId());
                }
                break;
            case offering:
                offering(p);
                break;
            case transaction:
                transaction(p);
                break;
            case pause:
                break;
            case bartering:
                bartering(p);
                break;
            case delegation:
                delegation(p, p.getAction(i));
                break;
        }
    }

    /**
     * Pour les tours 4 et 8 on calcul l'inventaire pour affecter récompense
     * au premier
     */
    public void afterActions() {
        
        // Calcul de l'inventaire aux tours 4, 8 et 12
        if (nbTurn % 4 == 0) {
            System.out.println("Calcul de l'inventaire...");
            calculateInventory();
        }
        
        // Avance du compteur de tour
        nbTurn++;
        
        // On réinitialise les actions et les villages des ressources
        players.forEach((p) -> {
            p.clearActions();
            p.clearResTakenVillage();
        });
        
        // On switch le premier joueur, il devient dernier à jouer
        Player p = players.get(0);
        players.remove(0);
        players.add(p);
    }

    /**
     * Exécute toutes les actions de tous les joueurs
     */
    public void executeActions() {

        // Initialisation des joueurs avant les actions
        prepareActions();

        System.out.println("**** Execution des actions ****");
        // Actions
        for (int i = 0; i < 6; i++) {
            for (Player p : players) {
                executeAction(i, p);
            }
        }

        afterActions();
    }

    private void calculateInventory() {
        ArrayList<Resource.Type> resourceType = new ArrayList<>();
        resourceType.add(Resource.Type.sel);
        resourceType.add(Resource.Type.orge);
        resourceType.add(Resource.Type.the);
        resourceType.add(Resource.Type.jade);
        resourceType.add(Resource.Type.or);

        for (Resource.Type type : resourceType) {
            Player winner = players.get(0);
            boolean equal = false;
            for (Player p : players) {
                if (p.getNbResources(type) > winner.getNbResources(type)) {
                    equal = false;
                    winner = p;
                } else if (p.getNbResources(type) == winner.getNbResources(type) && !p.equals(winner)) {
                    equal = true;
                }
            }
            if (equal == false && winner.getNbResources(type) > 0) {
                winner.setEconomicScore(winner.getEconomicScore() + 3);
            }
        }
    }

    public Player winnerPoliticalScore() {
        for (Region r : regions) {
            Player p = getPlayerByColor(r.getMaxDelegationPlayer());
            if (p != null) {
                p.setPoliticalScore(p.getPoliticalScore() + 1);
            }
        }

        Player winner = players.get(0);
        boolean equal = false;
        for (Player p : players) {
            if (p.getPoliticalScore() > winner.getPoliticalScore()) {
                winner = p;
                equal = false;
            } else if (p.getPoliticalScore() == winner.getPoliticalScore() && !p.equals(winner)) {
                equal = true;
            }
        }

        if (equal == false && winner.getPoliticalScore() > 0) {
            return winner;
        }
        return null;

    }

    public Player winnerReligiousScore() {
        Player winner = players.get(0);
        boolean equal = false;
        for (Player p : players) {
            if (p.getReligiousScore() > winner.getReligiousScore()) {
                winner = p;
                equal = false;
            } else if (p.getReligiousScore() == winner.getReligiousScore() && !p.equals(winner)) {
                equal = true;
            }
        }

        if (equal == false && winner.getReligiousScore() > 0) {
            return winner;
        }
        return null;
    }

    public Player winnerEconnomicScore() {
        Player winner = players.get(0);
        boolean equal = false;
        for (Player p : players) {
            if (p.getEconomicScore() > winner.getEconomicScore()) {
                winner = p;
                equal = false;
            } else if (p.getEconomicScore() == winner.getEconomicScore() && !p.equals(winner)) {
                equal = true;
            }
        }

        if (equal == false && winner.getEconomicScore() > 0) {
            return winner;
        }
        return null;
    }

    void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Player getPlayerByColor(String color) {
        Player p = null;

        for (Player player : players) {
            if (player.getColor().equals(color)) {
                p = player;
            }
        }
        return p;
    }
}
