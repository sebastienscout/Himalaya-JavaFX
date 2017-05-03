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
    public void regionsTest(){
        Region region6 = board.getRegionById(6);
        assertEquals(true, region6.getNeighbors().contains(board.getRegionById(3)));
        assertEquals(true, region6.getNeighbors().contains(board.getRegionById(5)));
        assertEquals(true, region6.getNeighbors().contains(board.getRegionById(7)));
        assertEquals(true, region6.getNeighbors().contains(board.getRegionById(8)));
    }
    
    @Test
    public void stupaTest(){
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

}
