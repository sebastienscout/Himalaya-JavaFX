package ihm;

import core.Action;
import core.Player;
import core.Village;
import ia.EvolutionaryAI;
import ia.RandomAI;
import java.util.Scanner;

public class PlayConsole extends Play {

    private final int nbTurnMax = 12;
    private final Scanner sc;

    public PlayConsole() {
        super();
        sc = new Scanner(System.in);
    }

    public void run() {

        //Initialisation
        System.out.println("Initialisation en cours ...");
        displayInfoBoard();
        initialPositionPlayers();

        //Les 12 tours
        while (board.getNbTurn() <= nbTurnMax) {
            refillVillages();
            //displayInfoBoard();

            selectActions();

            board.executeActions();
        }

        displayWinner();
    }

    private void selectActions() {
        board.getPlayers().forEach((p) -> {
            
            displayLine();
            displayPlayerInfos(p);
            
            if (p instanceof EvolutionaryAI) {
                //Actions pour l'IA evolutionnaire
                evolActions((EvolutionaryAI) p);
            } else if (p instanceof RandomAI) {
                //Actions pour l'IA random
                randomActions((RandomAI) p);
            } else {
                //Actions pour le joueur humain
                humanActions(p);
            }
        });
    }

    private void displayPlayerInfos(Player p) {
        /**
         * INFOS DU JOUEUR
         */
        System.out.println("**** Joueur " + p.getColor() + " ****");
        System.out.println("Position : Village " + p.getPosition().getId());
        System.out.println("Ressources : " + p.getResources());
        System.out.println("Nombre de yacks : " + p.getEconomicScore());
        System.out.print("Délégation [");
        p.getDelegations().keySet().forEach((region) -> {
            Integer regionID = region;
            int nbDelegation = p.getDelegations().get(region);
            System.out.print("Region " + regionID + " => " + nbDelegation + ", ");
        });
        System.out.println("]");
    }

    private void initialPositionPlayers() {
        int choiceVillage;
        //Pour premier tour
        for (Player p : board.getPlayers()) {

            if (p instanceof EvolutionaryAI || p instanceof RandomAI) {
                choiceVillage = p.calculateInitPosition();
            } else {
                System.out.println("Joueur " + p.getColor() + " Choisir le village de départ");
                choiceVillage = sc.nextInt();
                while (!board.getVillageById(choiceVillage).getResources().isEmpty()
                        || board.getVillageById(choiceVillage).getOrder() != null) {
                    System.out.println("Erreur il y a une commande ou des ressources sur ce village.");
                    System.out.println("Joueur " + p.getColor() + " Choisir le village de départ");
                    choiceVillage = sc.nextInt();
                }
            }

            System.out.println("Joueur " + p.getColor() + " se positionne en " + choiceVillage);
            p.setPosition(board.getVillageById(choiceVillage));
            board.addChoiceVillage(choiceVillage);
        }
    }

    @Override
    protected void humanActions(Player p) {
        int choice;

        System.out.println("Choisir parmis ces 6 actions :");
        for (int i = 0; i < 6; i++) {
            System.out.println("1 : ice");
            System.out.println("2 : stone");
            System.out.println("3 : soil");
            System.out.println("4 : delegation -> Récompense");
            System.out.println("5 : offering -> Récompense");
            System.out.println("6 : troc -> Récompense");
            System.out.println("7 : transaction");
            System.out.println("0 : pause");
            Action action = null;
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    displayLine();
                    action = new Action(Action.Type.ice);
                    displayLine();
                    break;
                case 2:
                    displayLine();
                    action = new Action(Action.Type.stone);
                    displayLine();
                    break;
                case 3:
                    displayLine();
                    action = new Action(Action.Type.soil);
                    displayLine();
                    break;
                case 4:
                    System.out.println("Sur quelle région voisine ? (Saisir id)");
                    choice = sc.nextInt();
                    action = new Action(Action.Type.delegation, choice);
                    break;
                case 5:
                    action = new Action(Action.Type.offering);
                    break;
                case 6:
                    action = new Action(Action.Type.bartering);
                    break;
                case 7:
                    action = new Action(Action.Type.transaction);
                    break;
                default:
                    action = new Action(Action.Type.pause);
                    break;
            }
            System.out.println(action);
            p.addAction(action);
        }
    }

    public void displayWinner() {

        System.out.println("**** Calcul de fin de partie ****");

        Player winner = board.winner();
        
        for (Player p : board.getPlayers()) {
            System.out.println("Joueur " + p.getColor() + " Economie : " + p.getEconomicScore());
            System.out.println("Joueur " + p.getColor() + " Religieux : " + p.getReligiousScore());
            System.out.println("Joueur " + p.getColor() + " Politique : " + p.getPoliticalScore());
            System.out.println("---");
        }

        System.out.println("Vainqueur : " + winner.getColor());
    }

    public void displayInfoBoard() {
        System.out.println("******* ETAT DU PLATEAU TOUR : " + board.getNbTurn() + " *******");
        for (Village village : board.getVillages()) {
            if (!village.getResources().isEmpty()) {
                System.out.println("Village " + village.getId() + " -> Ressources: " + village.getResources());
            } else if (village.getOrder() != null) {
                System.out.println("Village " + village.getId() + " -> Commande: " + village.getOrder().getResources());
            }
        }
    }

    public void displayLine() {
        System.out.println("-----------------");
    }

}
