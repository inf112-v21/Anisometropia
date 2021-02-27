package inf112.skeleton.app;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private GameMap gameMap;
    private GameLogic gameLogic;
    private Player player;

    @Test
    public void testPlayer() {
        TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
        GameLogic gameLogic = new GameLogic(simpleGameMap);
        Player player1 = gameLogic.getCurrentPlayer();
        gameLogic.getPlayerQueue().next();
        Player player2 = gameLogic.getCurrentPlayer();
        gameLogic.getPlayerQueue().next();
        assertEquals(player1,gameLogic.getCurrentPlayer());
    }


//    @BeforeEach
//    void setup() {
//        GameScreen gameScreen = new GameScreen();
//        GraphicalGameMap gameMap = gameScreen.getGameMap();
//        GameLogic gameLogic = gameScreen.gameLogic;
//        Player player = gameLogic.getCurrentPlayer();
//    }
//
//    // Test does not currently work. Setup is somehow wrong
//    @Test
//    public void rotatingNorthFacingPlayerOneUnitLeavesPlayerFacingEast() {
//        player.rotate(1);
//
//        assertEquals(1, player.getDirection());
//    }
}