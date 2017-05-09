package tests;

import core.Action;
import core.Board;
import core.Play;
import core.Player;
import core.Region;
import core.Resource;
import core.Village;
import java.util.ArrayList;
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

        player1.addAction(new Action(Action.Type.stone));
        player1.addAction(new Action(Action.Type.ice));
        player1.addAction(new Action(Action.Type.soil));
        player1.addAction(new Action(Action.Type.stone));
        player1.addAction(new Action(Action.Type.soil));
        player1.addAction(new Action(Action.Type.ice));

        board.executeActions();

        assertEquals(board.getVillageById(8), player1.getPosition());

    }

    @Test
    public void deleguationTest(){
        Player player1 = new Player("rouge", board.getVillageById(14));
        board.addPlayer(player1);
        
        Village village = board.getVillageById(18);
        village.removeOrder();
        village.getResources().clear();
        village.setOrder(board.getBagOrders().takeRandom());

        for (int i = 0; i < 20; i++) {
            player1.addResource(board.getBagResources().takeRandom());
        }
        
        player1.addAction(new Action(Action.Type.stone));
        player1.addAction(new Action(Action.Type.ice));
        player1.addAction(new Action(Action.Type.soil));
        player1.addAction(new Action(Action.Type.transaction));
        player1.addAction(new Action(Action.Type.pause));
        
        ArrayList<Region> neighbors = player1.getPosition().getRegions();
        int regionID = neighbors.get(0).getId();
        
        player1.addAction(new Action(Action.Type.delegation, regionID));
        
        board.executeActions();
        
        assertEquals(board.getVillageById(18), player1.getPosition());
        assertEquals(2, board.getRegionById(regionID).getDelegations().get(player1).intValue());
    }

    @Test
    public void stupaTest() {
        Player player1 = new Player("rouge", board.getVillageById(14));
        board.addPlayer(player1);
        
        Village village = board.getVillageById(18);        
        village.removeOrder();
        village.getResources().clear();
        village.setOrder(board.getBagOrders().takeRandom());

        for (int i = 0; i < 20; i++) {
            player1.addResource(board.getBagResources().takeRandom());
        }

        player1.addAction(new Action(Action.Type.stone));
        player1.addAction(new Action(Action.Type.ice));
        player1.addAction(new Action(Action.Type.soil));
        player1.addAction(new Action(Action.Type.transaction));
        player1.addAction(new Action(Action.Type.pause));
        player1.addAction(new Action(Action.Type.offering));
        
        board.executeActions();

        assertEquals(board.getVillageById(18), player1.getPosition());
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

        player1.addAction(new Action(Action.Type.stone));
        player1.addAction(new Action(Action.Type.ice));
        player1.addAction(new Action(Action.Type.soil));
        player1.addAction(new Action(Action.Type.pause));
        player1.addAction(new Action(Action.Type.pause));
        player1.addAction(new Action(Action.Type.transaction));
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
        assertEquals(25, board.getBagResources().getResources().size());
        
        player1.addAction(new Action(Action.Type.stone));
        player1.addAction(new Action(Action.Type.ice));
        player1.addAction(new Action(Action.Type.soil));
        player1.addAction(new Action(Action.Type.pause));
        player1.addAction(new Action(Action.Type.pause));
        player1.addAction(new Action(Action.Type.transaction));
        board.executeActions();
        
        assertEquals(10-nbResourcesInOrder, player1.getResources().size());
        assertEquals(25+nbResourcesInOrder, board.getBagResources().getResources().size());
        
        assertEquals(null, village.getOrder());
    }

}
