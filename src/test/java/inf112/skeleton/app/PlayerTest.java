package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;

import static inf112.skeleton.app.GameScreen.SCREEN_HEIGHT;
import static inf112.skeleton.app.GameScreen.SCREEN_WIDTH;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {
    GameScreen gameScreen;
    GameMap gameMap;

    /**
     * CURRENTLY the tests do not run headless
     * the game window will appear once for every test
     * let each game window run for 2 seconds, and then close
     */
    @Before
    public void testSetup() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("test");
        config.setIdleFPS(60);
        config.useVsync(true);
        config.setResizable(false);
        config.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameScreen = new GameScreen();
        new Lwjgl3Application(gameScreen, config);
        gameMap = gameScreen.getGameMap();
    }

    @Test
    public void testPlayerCanMoveX() {
        Player player = new Player(1,0, gameMap);
        assertTrue(player.canMove(1,0));
        assertTrue(player.canMove(-1,0));
    }

    @Test
    public void testPlayerCanMoveY() {
        Player player = new Player(0,1, gameMap);
        assertTrue(player.canMove(0,1));
        assertTrue(player.canMove(0,-1));
    }

    @Test
    public void testPlayerCannotMoveOffBoardX() {
        Player player = new Player(0, 0, gameMap);
        assertFalse(player.canMove(-1,0));
        player = new Player(gameMap.getWidth(), 0, gameMap);
        assertFalse(player.canMove(1, 0));
    }

    @Test
    public void testPlayerCannotMoveOffBoardY() {
        Player player = new Player(0, 0, gameMap);
        assertFalse(player.canMove(0,-1));
        player = new Player(0, gameMap.getHeight(), gameMap);
        assertFalse(player.canMove(0, 1));
    }

}
