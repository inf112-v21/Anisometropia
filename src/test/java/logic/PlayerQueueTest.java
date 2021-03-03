package logic;

import actor.Player;
import map.TextualGameMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerQueueTest {
    @Test
    public void test2PlayerQueue() {
        TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
        GameLogic gameLogic = new GameLogic(simpleGameMap);
        Player player1 = gameLogic.getCurrentPlayer();
        gameLogic.getPlayerQueue().next();
        Player player2 = gameLogic.getCurrentPlayer();
        gameLogic.getPlayerQueue().next();
        assertEquals(player1,gameLogic.getCurrentPlayer());
    }
}
