package tests;

import core.Action;
import core.BagOrders;
import core.BagResources;
import core.Board;
import core.Play;
import core.Player;
import core.Region;
import core.Village;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {

    Play play;
    Board board;

    @Before
    public void setUp() {
        play = new Play();
        board = play.getBoard();

    }

    @Test
    public void initTests() {
        assertEquals(1, board.getNbTurn());
        assertEquals(0, board.getCurrentPlayer());
    }

    @Test
    public void playerTests() {
        Player player1 = new Player("rouge", board.getVillageById(6));
        board.addPlayer(player1);

        assertEquals("rouge", board.getPlayers().get(board.getCurrentPlayer()).getColor());
        assertEquals(0, board.getPlayers().get(board.getCurrentPlayer()).getPoliticalScore());

        assertEquals(board.getVillageById(6), player1.getPosition());

        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.stone);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.ice);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.soil);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.stone);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.soil);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.ice);

        board.executeActions();

        assertEquals(board.getVillageById(8), player1.getPosition());

    }

//    @Test
//    public void bagResourcesTest() {
//        
//        //Test sur les cartes resources
//        BagResources br = new BagResources();
//        assertEquals(60, br.getResources().size());
//        br.takeRandom(); //On prend une carte random dans le sac
//        assertEquals(59, br.getResources().size());
//
//        //Test pour affecter des resources aux villages
//        Village village1 = board.getVillageById(1);
//        
//        village1.addResource(br.takeRandom());
//        assertEquals(58, br.getResources().size());
//        assertEquals(1, village1.getResources().size());
//    }
//    
//    @Test
//    public void bagOrdersTest(){
//        
//        //Test sur les cartes Commande
//        BagOrders bo = new BagOrders();
//        assertEquals(40, bo.getOrders().size());
//        
//        //Test affectation Commande à un village
//        Village v1 = board.getVillageById(1);
//        v1.setOrder(bo.takeRandom());
//        assertEquals(39, bo.getOrders().size());
//        assertNotEquals(null, v1.getOrder());
//    }
    @Test
    public void regionsTest() {
        Region region6 = board.getRegionById(6);
        assertEquals(true, region6.getNeighbors().contains(board.getRegionById(3)));
        assertEquals(true, region6.getNeighbors().contains(board.getRegionById(5)));
        assertEquals(true, region6.getNeighbors().contains(board.getRegionById(7)));
        assertEquals(true, region6.getNeighbors().contains(board.getRegionById(8)));
    }

    @Test
    public void stupaTest() {
        Player player1 = new Player("rouge", board.getVillageById(14));
        board.addPlayer(player1);

        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.stone);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.ice);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.soil);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.stone);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.offering);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.offering);
        board.executeActions();

        assertNotEquals(null, player1.getPosition().getStupa());
    }

    @Test
    public void transactionTest() {
        //Création du joueur sur le village 14
        Player player1 = new Player("rouge", board.getVillageById(14));
        board.addPlayer(player1);

        //Selection du village 18 (destination avec les actions)
        Village village = board.getVillageById(18);

        //On vérifie qu'il n'y a pas de commande ou resources pour test
        village.removeOrder();
        village.getResources().clear();

        //Ajout d'une resource random dans le village 18
        village.addResource(board.getBagResources().takeRandom());
        assertEquals(1, village.getResources().size());

//        //Pour ajouter resource au player pour test action
//        for (int i = 0; i < 10; i++) {
//            player1.addResource(board.getBagResources().takeRandom());
//        }
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.stone);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.ice);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.soil);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.pause);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.pause);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.transaction);
        board.executeActions();

        assertEquals(1, player1.getResources().size());
        assertEquals(0, village.getResources().size());

    }

    @Test
    public void orderTest() {
        //Création du joueur sur le village 14
        Player player1 = new Player("rouge", board.getVillageById(14));
        board.addPlayer(player1);

        //Selection du village 18 (destination avec les actions)
        Village village = board.getVillageById(18);

        //On vérifie qu'il n'y a pas de commande ou resources pour test
        village.removeOrder();
        village.getResources().clear();

        //Ajout d'une commande random dans le village 18
        village.setOrder(board.getBagOrders().takeRandom());
        assertNotEquals(null, village.getOrder());
        int nbResourcesInOrder = village.getOrder().getResources().size();

        //Pour ajouter resource au player pour test action
        for (int i = 0; i < 10; i++) {
            player1.addResource(board.getBagResources().takeRandom());
        }
        assertEquals(10, player1.getResources().size());
        
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.stone);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.ice);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.soil);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.pause);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.pause);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.transaction);
        board.executeActions();
        
        assertNotEquals(10-nbResourcesInOrder, player1.getResources().size());
        assertEquals(null, village.getOrder());
    }

}
