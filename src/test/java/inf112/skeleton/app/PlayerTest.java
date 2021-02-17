package inf112.skeleton.app;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private GameScreen gameScreen;
    private GameMap gameMap;
    private GameLogic gameLogic;
    private Player player;

    @BeforeEach
    void setup() {
        GameScreen gameScreen = new GameScreen();
        GameMap gameMap = gameScreen.getGameMap();
        GameLogic gameLogic = gameScreen.gameLogic;
        Player player = gameLogic.getPlayer();
    }

    // Test does not currently work. Setup is somehow wrong
    @Test
    public void rotatingNorthFacingPlayerOneUnitLeavesPlayerFacingEast() {
        player.rotate(1);

        assertEquals(1, player.getDirection());
    }
}
