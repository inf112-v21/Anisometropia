package logic;

import actor.Player;
import map.TextualGameMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerQueueTest {
    @Test
    public void test2PlayerQueue() {
        TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
        PlayerQueue playerQueue = new PlayerQueue();
        Player player1 = new Player(1,1,"player1", simpleGameMap, 0);
        Player player2 = new Player(2,1,"player2", simpleGameMap, 1);
        playerQueue.add(player1);
        playerQueue.add(player2);
        GameLogic gameLogic = new GameLogic(simpleGameMap, playerQueue);
        gameLogic.getPlayerQueue().next();
        gameLogic.getPlayerQueue().next();
        assertEquals(player1,gameLogic.getCurrentPlayer());
    }
}
