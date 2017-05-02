package tests;

import core.Board;
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
        assertEquals("rouge", board.getPlayers().get(board.getCurrentPlayer()).getColor());
        assertEquals(0, board.getPlayers().get(board.getCurrentPlayer()).getPoliticalScore());
    }
}
