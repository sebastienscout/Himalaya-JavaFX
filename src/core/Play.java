package core;

import java.util.ArrayList;
import java.util.Scanner;

public class Play {

    private Board board;

    public Play() {
        board = new Board();
        initVillagesAndRegions();
        initFirstTurn();
    }

    public void addPlayer(String color, int villageID) {
        board.addPlayer(new Player(color, board.getVillageById(villageID)));
    }

    public void run() {
        while (board.getNbTurn() <= 12) {
            System.out.println("Nombre de tour : " + board.getNbTurn());
            for (Player p : board.getPlayers()) {
                for (int i = 0; i < 6; i++) {
                    int choice = (int) (Math.random() * 6);
                    Action action = null;
                    switch (choice) {
                        case 1:
                            action = new Action(Action.Type.ice);
                            break;
                        case 2:
                            action = new Action(Action.Type.stone);
                            break;
                        case 3:
                            action = new Action(Action.Type.soil);
                            break;
                        case 4:
                            action = new Action(Action.Type.delegation);
                            break;
                        case 5:
                            action = new Action(Action.Type.offering);
                            break;
                        case 6:
                            action = new Action(Action.Type.transaction);
                            break;
                        case 0:
                            action = new Action(Action.Type.pause);
                            break;
                    }
                    System.out.println(action);
                    p.addAction(action);
                }
            }
            board.executeActions();
        }
    }

    /**
     * Initialisation du plateau
     */
    private void initVillagesAndRegions() {

        //Initialisation des villages
        Village village1 = new Village(1, Village.Type.temple);
        Village village2 = new Village(2, Village.Type.house);
        Village village3 = new Village(3, Village.Type.temple);
        Village village4 = new Village(4, Village.Type.house);
        Village village5 = new Village(5, Village.Type.temple);
        Village village6 = new Village(6, Village.Type.monastery);
        Village village7 = new Village(7, Village.Type.monastery);
        Village village8 = new Village(8, Village.Type.house);
        Village village9 = new Village(9, Village.Type.temple);
        Village village10 = new Village(10, Village.Type.monastery);
        Village village11 = new Village(11, Village.Type.temple);
        Village village12 = new Village(12, Village.Type.temple);
        Village village13 = new Village(13, Village.Type.monastery);
        Village village14 = new Village(14, Village.Type.monastery);
        Village village15 = new Village(15, Village.Type.house);
        Village village16 = new Village(16, Village.Type.temple);
        Village village17 = new Village(17, Village.Type.monastery);
        Village village18 = new Village(18, Village.Type.temple);
        Village village19 = new Village(19, Village.Type.house);
        Village village20 = new Village(20, Village.Type.house);

        //Initialisation des routes
        village1.addRoad(Road.soil, village2);
        village1.addRoad(Road.stone, village7);
        village1.addRoad(Road.ice, village8);
        village2.addRoad(Road.soil, village1);
        village2.addRoad(Road.stone, village3);
        village3.addRoad(Road.stone, village2);
        village3.addRoad(Road.ice, village6);
        village3.addRoad(Road.soil, village4);
        village4.addRoad(Road.soil, village3);
        village4.addRoad(Road.ice, village5);
        village5.addRoad(Road.ice, village4);
        village5.addRoad(Road.soil, village11);
        village5.addRoad(Road.stone, village6);
        village6.addRoad(Road.stone, village5);
        village6.addRoad(Road.ice, village3);
        village6.addRoad(Road.soil, village7);
        village7.addRoad(Road.soil, village6);
        village7.addRoad(Road.ice, village10);
        village7.addRoad(Road.stone, village1);
        village8.addRoad(Road.ice, village1);
        village8.addRoad(Road.soil, village9);
        village9.addRoad(Road.soil, village8);
        village9.addRoad(Road.ice, village14);
        village9.addRoad(Road.stone, village15);
        village10.addRoad(Road.stone, village11);
        village10.addRoad(Road.ice, village7);
        village10.addRoad(Road.soil, village14);
        village11.addRoad(Road.stone, village10);
        village11.addRoad(Road.soil, village5);
        village11.addRoad(Road.ice, village12);
        village12.addRoad(Road.ice, village11);
        village12.addRoad(Road.stone, village18);
        village12.addRoad(Road.soil, village13);
        village13.addRoad(Road.soil, village12);
        village13.addRoad(Road.ice, village17);
        village13.addRoad(Road.stone, village14);
        village14.addRoad(Road.stone, village13);
        village14.addRoad(Road.soil, village10);
        village14.addRoad(Road.ice, village9);
        village15.addRoad(Road.stone, village9);
        village15.addRoad(Road.soil, village16);
        village16.addRoad(Road.soil, village15);
        village16.addRoad(Road.ice, village20);
        village16.addRoad(Road.stone, village17);
        village17.addRoad(Road.stone, village16);
        village17.addRoad(Road.soil, village18);
        village17.addRoad(Road.ice, village13);
        village18.addRoad(Road.soil, village17);
        village18.addRoad(Road.stone, village12);
        village18.addRoad(Road.ice, village19);
        village19.addRoad(Road.ice, village18);
        village19.addRoad(Road.soil, village20);
        village20.addRoad(Road.soil, village19);
        village20.addRoad(Road.ice, village16);

        //Initialisation des Régions
        Region region1 = new Region(1);
        Region region2 = new Region(2);
        Region region3 = new Region(3);
        Region region4 = new Region(4);
        Region region5 = new Region(5);
        Region region6 = new Region(6);
        Region region7 = new Region(7);
        Region region8 = new Region(8);

        // Add regions to villages
        village1.addRegion(region2);
        village1.addRegion(region3);
        village2.addRegion(region2);
        village3.addRegion(region2);
        village3.addRegion(region1);
        village4.addRegion(region1);
        village5.addRegion(region1);
        village5.addRegion(region4);
        village6.addRegion(region1);
        village6.addRegion(region2);
        village6.addRegion(region4);
        village7.addRegion(region2);
        village7.addRegion(region3);
        village7.addRegion(region4);
        village8.addRegion(region3);
        village9.addRegion(region3);
        village9.addRegion(region6);
        village10.addRegion(region4);
        village10.addRegion(region5);
        village10.addRegion(region3);
        village11.addRegion(region4);
        village11.addRegion(region5);
        village12.addRegion(region5);
        village12.addRegion(region7);
        village13.addRegion(region5);
        village13.addRegion(region6);
        village13.addRegion(region7);
        village14.addRegion(region3);
        village14.addRegion(region5);
        village14.addRegion(region6);
        village15.addRegion(region6);
        village16.addRegion(region6);
        village16.addRegion(region8);
        village17.addRegion(region6);
        village17.addRegion(region7);
        village17.addRegion(region8);
        village18.addRegion(region7);
        village18.addRegion(region8);
        village19.addRegion(region8);
        village20.addRegion(region8);

        //Affection villages
        board.addVillage(village1);
        board.addVillage(village2);
        board.addVillage(village3);
        board.addVillage(village4);
        board.addVillage(village5);
        board.addVillage(village6);
        board.addVillage(village7);
        board.addVillage(village8);
        board.addVillage(village9);
        board.addVillage(village10);
        board.addVillage(village11);
        board.addVillage(village12);
        board.addVillage(village13);
        board.addVillage(village14);
        board.addVillage(village15);
        board.addVillage(village16);
        board.addVillage(village17);
        board.addVillage(village18);
        board.addVillage(village19);
        board.addVillage(village20);

        //Affectation Régions
        board.addRegion(region1);
        board.addRegion(region2);
        board.addRegion(region3);
        board.addRegion(region4);
        board.addRegion(region5);
        board.addRegion(region6);
        board.addRegion(region7);
        board.addRegion(region8);

    }

    /**
     * Initialisation au premier tour Remplissage des villages de resources et
     * commandes
     */
    private void initFirstTurn() {
        int nbVillagesToAffectation = 5;
        int randVillage;
        int i = 1;
        Village village;
        while (i <= nbVillagesToAffectation) {
            randVillage = (int) (Math.random() * 20 + 1);
            village = board.getVillageById(randVillage);
            if (village.getOrder() == null && village.getResources().isEmpty()) {
                for (int j = 1; j <= nbVillagesToAffectation; j++) {
                    village.addResource(board.getBagResources().takeRandom());
                }
                i++;
            }
        }

        i = 1;
        while (i <= nbVillagesToAffectation) {
            randVillage = (int) (Math.random() * 20 + 1);
            village = board.getVillageById(randVillage);
            if (village.getResources().isEmpty() && village.getOrder() == null) {
                village.setOrder(board.getBagOrders().takeRandom());
                i++;
            }
        }
    }

    /**
     * Quand un village n'a plus de resources on en remet dans un autre
     */
    public void reFillVillageResource() {
        int randVillage = (int) (Math.random() * 20 + 1);
        Village village = board.getVillageById(randVillage);
        if (village.getResources().isEmpty() && village.getOrder() == null) {
            village.addResource(board.getBagResources().takeRandom());
        }
    }

    /**
     * Quand un village n'a plus de commande on en remet dans un autre
     */
    public void reFillVillageOrder() {
        int randVillage = (int) (Math.random() * 20 + 1);
        Village village = board.getVillageById(randVillage);
        if (village.getResources().isEmpty() && village.getOrder() == null) {
            village.setOrder(board.getBagOrders().takeRandom());
        }
    }

    public Board getBoard() {
        return board;
    }

}
