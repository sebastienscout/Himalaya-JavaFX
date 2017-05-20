package core;

import ia.EvolutionaryAI;
import ia.RandomAI;
import java.util.ArrayList;

public class Play {

    protected static Board board;

    public Play() {
        board = new Board();
        initVillagesAndRegions();
        initFirstTurn();
    }

    public void addPlayer(String color, int villageID) {
        board.addPlayer(new Player(color, board.getVillageById(villageID)));
    }

    public void addEvolAI(String color, int villageID) {
        board.addPlayer(new EvolutionaryAI(color, board.getVillageById(villageID)));
    }

    public void addRandomAI(String color, int villageID) {
        board.addPlayer(new RandomAI(color, board.getVillageById(villageID)));
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
        village1.addRoad(Road.soil, 2);
        village1.addRoad(Road.stone, 7);
        village1.addRoad(Road.ice, 8);
        village2.addRoad(Road.soil, 1);
        village2.addRoad(Road.stone, 3);
        village3.addRoad(Road.stone, 2);
        village3.addRoad(Road.ice, 6);
        village3.addRoad(Road.soil, 4);
        village4.addRoad(Road.soil, 3);
        village4.addRoad(Road.ice, 5);
        village5.addRoad(Road.ice, 4);
        village5.addRoad(Road.soil, 11);
        village5.addRoad(Road.stone, 6);
        village6.addRoad(Road.stone, 5);
        village6.addRoad(Road.ice, 3);
        village6.addRoad(Road.soil, 7);
        village7.addRoad(Road.soil, 6);
        village7.addRoad(Road.ice, 10);
        village7.addRoad(Road.stone, 1);
        village8.addRoad(Road.ice, 1);
        village8.addRoad(Road.soil, 9);
        village9.addRoad(Road.soil, 8);
        village9.addRoad(Road.ice, 14);
        village9.addRoad(Road.stone, 15);
        village10.addRoad(Road.stone, 11);
        village10.addRoad(Road.ice, 7);
        village10.addRoad(Road.soil, 14);
        village11.addRoad(Road.stone, 10);
        village11.addRoad(Road.soil, 5);
        village11.addRoad(Road.ice, 12);
        village12.addRoad(Road.ice, 11);
        village12.addRoad(Road.stone, 18);
        village12.addRoad(Road.soil, 13);
        village13.addRoad(Road.soil, 12);
        village13.addRoad(Road.ice, 17);
        village13.addRoad(Road.stone, 14);
        village14.addRoad(Road.stone, 13);
        village14.addRoad(Road.soil, 10);
        village14.addRoad(Road.ice, 9);
        village15.addRoad(Road.stone, 9);
        village15.addRoad(Road.soil, 16);
        village16.addRoad(Road.soil, 15);
        village16.addRoad(Road.ice, 20);
        village16.addRoad(Road.stone, 17);
        village17.addRoad(Road.stone, 16);
        village17.addRoad(Road.soil, 18);
        village17.addRoad(Road.ice, 13);
        village18.addRoad(Road.soil, 17);
        village18.addRoad(Road.stone, 12);
        village18.addRoad(Road.ice, 19);
        village19.addRoad(Road.ice, 18);
        village19.addRoad(Road.soil, 20);
        village20.addRoad(Road.soil, 19);
        village20.addRoad(Road.ice, 16);

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
    public void reFillVillageResource(ArrayList<Village> villagesToIgnore) {
        boolean test = false;
        //Tant qu'on a pas de village
        while (!test) {
            int randVillage = (int) (Math.random() * 20 + 1);
            Village village = board.getVillageById(randVillage);
            if (!villagesToIgnore.contains(village)) {
                for (int i = 0; i < 5; i++) {
                    village.addResource(board.getBagResources().takeRandom());
                }
                villagesToIgnore.add(village);
                test = true;
            }
        }
    }

    /**
     * Quand un village n'a plus de commande on en remet dans un autre
     */
    public void reFillVillageOrder(ArrayList<Village> villagesToIgnore) {
        boolean test = false;
        //Tant qu'on a pas de village
        while (!test) {
            int randVillage = (int) (Math.random() * 20 + 1);
            Village village = board.getVillageById(randVillage);
            if (!villagesToIgnore.contains(village)) {
                village.setOrder(board.getBagOrders().takeRandom());
                villagesToIgnore.add(village);
                test = true;
            }
        }
    }

    //Test si il manque une commande ou moins de 5 villages avec resources
    public void testVillages() {

        int nbVillagesWithResources = 0;
        int nbVillagesWithOrders = 0;
        int max = 5;
        for (Village village : board.getVillages()) {
            if (village.getOrder() != null) {
                nbVillagesWithOrders++;
            }
            if (!village.getResources().isEmpty()) {
                nbVillagesWithResources++;
            }
        }

        ArrayList<Village> villagesToIgnore = new ArrayList<>();
        for (Village village : board.getVillages()) {
            if (!village.getResources().isEmpty() || village.getOrder() != null) {
                villagesToIgnore.add(village);
            }
        }
        for (Player player : board.getPlayers()) {
            villagesToIgnore.add(player.getPosition());
        }

        while (nbVillagesWithResources < max) {
            System.out.println("Village a remplir  : " + (max - nbVillagesWithResources));
            reFillVillageResource(villagesToIgnore);
            nbVillagesWithResources++;
        }
        while (nbVillagesWithOrders < max) {
            reFillVillageOrder(villagesToIgnore);
            nbVillagesWithOrders++;
        }
    }

    public Board getBoard() {
        return board;
    }

//    public void test() {
//        addPlayer("Michel", 1);
//        addPlayer("toto", 1);
//        addPlayer("TATA", 1);
//        for (int i = 0; i < 1000; i++) {
//            Board b = (Board) board.clone();
//            System.out.println("Clone #" + i + " => " + b);
//            System.out.println("    => Bag Resources " + b.getBagResources());
//            System.out.println("    => Bag Resources Detail " + b.getBagResources().getResources());
//
//            System.out.println("    => Bag Order " + b.getBagOrders());
//            System.out.println("    => Bag Order Detail " + b.getBagOrders().getOrders());
//
//            System.out.println("    => Village " + b.getVillages());
//            for (Village village : b.getVillages()) {
//                System.out.println("       => Village ID " + village.getId());
//                for (Resource resource : village.getResources()) {
//                    System.out.println("            => Village resources " + resource.hashCode());
//                }
//
//            }
//
//            for (Player player : b.getPlayers()) {
//                System.out.println("        => Player " + player.getColor());
//                System.out.println("        => Player " + player);
//            }
//        }
//    }
}
