package ihm;

import core.Action;
import core.Delegation;
import core.Play;
import core.Player;
import core.Region;
import core.Resource;
import core.Road;
import core.Village;
import ia.RandomAI;
import java.util.ArrayList;
import java.util.Scanner;

public class PlayConsole extends Play {

    private static int nbTurnMax = 12;

    public PlayConsole() {
        super();
    }

    public void run() {
        int choice;
        int choiceVillage;
        Village v;
        Scanner sc = new Scanner(System.in);
        System.out.println("**** HIMALAYA ****");
        displayLine();
        //Initialisation
        System.out.println("Initialisation en cours ...");
        displayInfoBoard();

        //Pour premier tour
        for (Player p : board.getPlayers()) {

            //Choisir position de départ
            System.out.println("Joueur " + p.getColor()
                    + " Choisir le village de départ (il ne doit pas y"
                    + " avoir de ressources ou de commande) ");
            choiceVillage = sc.nextInt();
            while (!board.getVillageById(choiceVillage).getResources().isEmpty()
                    || board.getVillageById(choiceVillage).getOrder() != null) {
                System.out.println("Erreur il y a une commande ou des ressources"
                        + " sur ce village.");
                System.out.println("Choisir le village de départ (il ne doit pas y"
                        + " avoir de ressources ou de commande) ");
                choiceVillage = sc.nextInt();
            }
            p.setPosition(board.getVillageById(choiceVillage));
        }

        //Les 12 tours
        while (board.getNbTurn() <= nbTurnMax) {
            testVillages();
            displayInfoBoard();
            //Pour tous les joueurs
            for (Player p : board.getPlayers()) {
                displayLine();
                /**
                 * INFOS DU JOUEURS *
                 */
                System.out.println("**** Joueur " + p.getColor() + " ****");
                System.out.println("Votre position : Village " + p.getPosition().getId());
                System.out.print("Vos Resources [ ");
                for (Resource res : p.getResources()) {
                    System.out.print(res.getType() + " ");
                }
                System.out.print("]");
                System.out.println("");
                System.out.println("score éco : " + p.getEconomicScore());
                System.out.println("score politique : " + p.getPoliticalScore());
                System.out.println("score religieux : " + p.getReligiousScore());
                System.out.print("Délégation[ ");
                for (Region region : p.getDelegations().keySet()) {

                    Integer regionID = region.getId();
                    int nbDelegation = p.getDelegations().get(region);
                    System.out.print("Region " + regionID + " => " + nbDelegation + ", ");

                }
                System.out.println("]");

                /**
                 * ACTIONS *
                 */
                if (!(p instanceof RandomAI)) {
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
                                //System.out.println("Nouvelle Position : Village " + p.getPosition().getDestVillage(Road.ice).getId());
                                displayLine();
                                break;
                            case 2:
                                displayLine();
                                action = new Action(Action.Type.stone);
                                //System.out.println("Nouvelle Position : Village " + p.getPosition().getDestVillage(Road.stone).getId());
                                displayLine();
                                break;
                            case 3:
                                displayLine();
                                action = new Action(Action.Type.soil);
                                //System.out.println("Nouvelle Position : Village " + p.getPosition().getDestVillage(Road.soil).getId());
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
                } else {
                    for (int i = 0; i < 6; i++) {
                        Action action = ((RandomAI) p).getRandomAction();
                        p.addAction(action);
                    }
                }
            }
            board.executeActions();
        }
        
        displayWinner();

    }

    public void displayWinner() {

        Player winnerEco = board.winnerEconnomicScore();
        Player winnerPol = board.winnerPoliticalScore();
        Player winnerReg = board.winnerReligiousScore();
        
        if (winnerEco != null) {
            System.out.println("Econnomie : " + winnerEco.getColor() + " (" + winnerEco.getEconomicScore() + ").");
        } else {
            System.out.println("Egalité du score Econnomie.");
        }
        if (winnerPol != null) {
            System.out.println("Political : " + winnerPol.getColor() + " (" + winnerPol.getPoliticalScore() + ").");
        } else {
            System.out.println("Egalité du score Political.");
        }
        if (winnerReg != null) {
            System.out.println("Political : " + winnerReg.getColor() + " (" + winnerReg.getReligiousScore() + ").");
        } else {
            System.out.println("Egalité du score Regligieux.");
        }
    }

    public void displayInfoBoard() {
        System.out.println("******* ETAT DU PLATEAU TOUR : " + board.getNbTurn() + " *******");
        for (Village village : board.getVillages()) {
            if (!village.getResources().isEmpty()) {
                System.out.print("Village " + village.getId() + " -> Resource[");
                for (Resource res : village.getResources()) {
                    System.out.print(res.getType() + ", ");
                }
                System.out.println(" ]");
            } else if (village.getOrder() != null) {
                System.out.print("Village " + village.getId() + " -> Commande[");
                for (Resource res : village.getOrder().getResources()) {
                    System.out.print(res.getType() + ", ");
                }
                System.out.println("]");
            }
        }
    }

    public void displayLine() {
        System.out.println("-----------------");
    }

}
