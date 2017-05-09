package tests;

import core.Play;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    Play play;

    @Before
    public void setUp() {
        play = new Play();
    }

    @Test
    public void gameTest() {
        play.addPlayer("rouge", 12);
        play.addPlayer("bleu", 5);
        play.addPlayer("jaune", 8);
        play.addPlayer("vert", 19);

        assertEquals(4, play.getBoard().getPlayers().size());
        assertEquals(play.getBoard().getVillageById(12), play.getBoard().getPlayers().get(0).getPosition());
        assertEquals(play.getBoard().getVillageById(5), play.getBoard().getPlayers().get(1).getPosition());
        assertEquals(play.getBoard().getVillageById(8), play.getBoard().getPlayers().get(2).getPosition());
        assertEquals(play.getBoard().getVillageById(19), play.getBoard().getPlayers().get(3).getPosition());
    }

    @Test
    public void runTest() {
        play.addPlayer("rouge", 12);

        play.run();
    }

}
