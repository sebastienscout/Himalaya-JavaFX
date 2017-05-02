package tests;

import core.Action;
import core.Board;
import core.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardTest {
    
    Board board;
    
    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void initTests() {
        assertEquals(1, board.getNbTurn());
        assertEquals(0, board.getCurrentPlayer());
    }
    
    @Test
    public void playerTests(){
        
        board.initVillagesAndRegions();
        
        Player player1 = new Player("rouge", board.getVillageById(6));
        board.addPlayer(player1);
        
        assertEquals("rouge", board.getPlayers().get(board.getCurrentPlayer()).getColor());
        assertEquals(0, board.getPlayers().get(board.getCurrentPlayer()).getPoliticalScore());
        
        assertEquals(board.getVillageById(6), player1.getPosition());
        
        System.out.println(player1.getPosition());
        
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.stone);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.ice);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.soil);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.stone);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.soil);
        board.getPlayers().get(board.getCurrentPlayer()).addAction(Action.ice);
        
        board.executeActions();
        
        assertEquals(board.getVillageById(8), player1.getPosition());
        
    }
}
