package core;

import ia.EvolutionaryAI;
import ia.Population;
import ia.RandomAI;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class Play {

    protected static Board board;

    public Play() {
        board = new Board();
        initVillagesAndRegions();
        initFirstTurn();
    }

    public void addPlayer(String color) {
        Player player = new Player(color);
        player.setBoard(board);
        board.addPlayer(player);       
    }

    public void addEvolAI(String color) {
        EvolutionaryAI ai = new EvolutionaryAI(color);
        ai.setBoard(board);
        board.addPlayer(ai);
    }

    public void addRandomAI(String color) {
        RandomAI ai = new RandomAI(color);
        ai.setBoard(board);
        board.addPlayer(ai);
    }
    
    protected void evolActions(EvolutionaryAI p) {
        // population size of the parents
        int mu = 20;
        // population size of childrens
        int lambda = 1000;
        // tournament size for selection
        int tournamentSize = 2;
        // rates of crossOver and mutation
        double crossOverRate = 0.8;
        // rates of mutation
        double mutationRate = 1.0;
        // maximum number of generation
        int maxGeneration = 100;

        Population population = new Population();

        p.run(population, mu, lambda, tournamentSize, crossOverRate, mutationRate, maxGeneration);
        
        for (Action action : p.getActions()) {
            p.addAction(action);
        }
    }
    
    protected abstract void humanActions(Player p);

    protected void randomActions(RandomAI p) {
        for (int i = 0; i < 6; i++) {
            Action action = ((RandomAI) p).getRandomAction();
            p.addAction(action);
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
        village1.getRegions().addAll(Arrays.asList(2,3));
        village2.getRegions().addAll(Arrays.asList(2));
        village3.getRegions().addAll(Arrays.asList(2,1));
        village4.getRegions().addAll(Arrays.asList(1));
        village5.getRegions().addAll(Arrays.asList(1,4));
        village6.getRegions().addAll(Arrays.asList(1,2,4));
        village7.getRegions().addAll(Arrays.asList(2,3,4));
        village8.getRegions().addAll(Arrays.asList(3));
        village9.getRegions().addAll(Arrays.asList(3,6));
        village10.getRegions().addAll(Arrays.asList(4,5,3));
        village11.getRegions().addAll(Arrays.asList(4,5));
        village12.getRegions().addAll(Arrays.asList(5,7));
        village13.getRegions().addAll(Arrays.asList(5,6,7));
        village14.getRegions().addAll(Arrays.asList(3,5,6));
        village15.getRegions().addAll(Arrays.asList(6));
        village16.getRegions().addAll(Arrays.asList(6,8));
        village17.getRegions().addAll(Arrays.asList(6,7,8));
        village18.getRegions().addAll(Arrays.asList(7,8));
        village19.getRegions().addAll(Arrays.asList(8));
        village20.getRegions().addAll(Arrays.asList(8));
        
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
     * @param villagesToIgnore
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
     * @param villagesToIgnore
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

}
