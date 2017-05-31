package core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class Board {

    private ArrayList<Player> players;
    private ArrayList<Village> villages;
    private ArrayList<Region> regions;
    private BagResources bagResources;
    private BagOrders bagOrders;
    private ArrayList<Integer> choiceBegining;
    private int nbTurn;
    private int currentPlayer;

    public Board() {
        nbTurn = 1;
        currentPlayer = 0;
        players = new ArrayList<>();
        choiceBegining = new ArrayList<>();
        villages = new ArrayList<>();
        regions = new ArrayList<>();
        bagResources = new BagResources();
        bagOrders = new BagOrders(40);
    }

    /**
     * Constructeur par copie
     *
     * @param board
     */
    public Board(Board board) {
        this.currentPlayer = board.currentPlayer;
        this.nbTurn = board.nbTurn;
        this.players = new ArrayList<>();
        this.villages = new ArrayList<>();
        this.regions = new ArrayList<>();
        this.choiceBegining = new ArrayList<>();
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

    public ArrayList<Integer> getChoiceBegining() {
        return choiceBegining;
    }

    public void addChoiceVillage(int i) {
        choiceBegining.add(i);
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
     * Effectue une transaction => - Si il y a des ressources sur le village on
     * récupère la ressource la + faible - Si il y a une commande on donne nos
     * ressources pour honorer la commande
     *
     * @param p Joueur
     */
    private void transaction(Player p) {
        Village village = p.getPosition();

        if (village.getResources().isEmpty() && village.getOrder() != null) {
            manageOrder(village, p);
        } else if (village.getResources().size() > 0 && village.getOrder() == null && p.canTakeResource()) {
            manageResource(village, p);
        }
    }

    private void manageResource(Village village, Player p) {
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

    private void manageOrder(Village village, Player p) {
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
    }

    /**
     * Ajoute une ou plusieurs délégations sur une région voisine du village
     *
     * @param p
     * @param action
     */
    private void delegation(Player p, Action action) {
        // Verification commande completée, et moins de 2 actions
        if (p.canPutDelegation()) {
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
            p.addDelegationPut(p.getPosition());
        }
    }

    /**
     * Effectue une offrande sur le village
     *
     * @param p
     */
    private void offering(Player p) {
        // Verification commande completée, et moins de 2 actions
        if (p.canPutStupa()) {
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
            }
        }
    }

    /**
     * Effectue le troc pour récupèrer yack sur commande (point éco)
     *
     * @param p Player
     */
    public void bartering(Player p, boolean graphic) {
        if (p.canBartering()) {
            p.setNbTransactionDone(p.getNbTransactionDone() + 1);
            p.setEconomicScore(p.getEconomicScore() + p.getNbYacksOrder());
            p.addBarteringVillage(p.getPosition());

            if (graphic) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Troc");
                    alert.setHeaderText(null);
                    alert.setContentText("Le joueur " + p.getColor() + " choisi de faire un troc : " + p.getNbYacksOrder() + " yacks.");
                    alert.showAndWait();
                });
            }
        }

    }

    /**
     * Exécution des actions après plannification d'un joueur en particulier
     *
     * @param i numéro de l'action dans l'ordre de la plannification
     * @param p Player
     */
    public void executeAction(int i, Player p, boolean graphic) {
        switch (p.getAction(i).getType()) {
            case stone:
                if (p.getPosition().getDestVillage(Road.stone) != null) {
                    p.setPosition(getVillageById(p.getPosition().getDestVillage(Road.stone)));
                }
                break;
            case soil:
                if (p.getPosition().getDestVillage(Road.soil) != null) {
                    p.setPosition(getVillageById(p.getPosition().getDestVillage(Road.soil)));
                }
                break;
            case ice:
                if (p.getPosition().getDestVillage(Road.ice) != null) {
                    p.setPosition(getVillageById(p.getPosition().getDestVillage(Road.ice)));
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
                bartering(p, graphic);
                break;
            case delegation:
                delegation(p, p.getAction(i));
                break;
        }
    }

    /**
     * Pour les tours 4 et 8 on calcul l'inventaire pour affecter récompense au
     * premier
     */
    public void afterActions() {

        if (nbTurn % 4 == 0) {
            System.out.println("Calcul de l'inventaire...");
            calculateInventory();
        }

        nbTurn++;

        players.forEach((p) -> {
            p.clearActions();
            p.setCompletedOrder(false, 0, 0);
            p.setNbTransactionDone(0);
        });

        Player p = players.get(0);
        players.remove(0);
        players.add(p);
    }

    /**
     * Exécute toutes les actions de tous les joueurs
     */
    public void executeActions() {

        System.out.println("**** Execution des actions ****");
        // Actions
        for (int i = 0; i < 6; i++) {
            for (Player p : players) {
                executeAction(i, p, false);
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

        for (Player p : players) {
            p.setPoliticalScore(0);
        }

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

    public Player winner() {
        Player winner = null;
        Player playerECO = winnerEconnomicScore();
        Player playerREL = winnerReligiousScore();
        Player playerPOL = winnerPoliticalScore();

        HashMap<Player, Integer> nbVictory = new HashMap<>();
        players.forEach((p) -> {
            nbVictory.put(p, 0);
        });

        if (playerECO != null) {
            nbVictory.replace(playerECO, nbVictory.get(playerECO) + 1);
        }
        if (playerPOL != null) {
            nbVictory.replace(playerPOL, nbVictory.get(playerPOL) + 1);
        }
        if (playerREL != null) {
            nbVictory.replace(playerREL, nbVictory.get(playerREL) + 1);
        }

        Map.Entry<Player, Integer> maxVictoryPlayer = Collections.max(nbVictory.entrySet(), Map.Entry.comparingByValue());
        if (maxVictoryPlayer.getValue() >= 2) {
            winner = maxVictoryPlayer.getKey();
        } else if (playerECO != null) {
            winner = playerECO;
        } else if (playerPOL != null) {
            winner = playerPOL;
        } else if (playerREL != null) {
            winner = playerREL;
        }

        return winner;

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
