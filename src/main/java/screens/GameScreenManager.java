package screens;

import launcher.GameApplication;
import logic.PlayerQueue;
import map.GraphicalGameMap;

import java.util.HashMap;

public class GameScreenManager {
    public final GameApplication gameApplication;
    private HashMap<STATE, AbstractScreen> gameScreens;
    public enum STATE {
        MENU,
        PLAY,
        LOCAL_SETUP,
        ON_NET_SETUP
    }

    PlayScreen playScreen;

    public GameScreenManager(GameApplication gameApplication) {
        this.gameApplication = gameApplication;
        initializeGameScreens();
        setScreen(STATE.MENU);
    }

    private void initializeGameScreens() {
        this.gameScreens = new HashMap<>();
        this.gameScreens.put(STATE.MENU, new MainMenuScreen(gameApplication));
        this.gameScreens.put(STATE.LOCAL_SETUP, new LocalSetupScreen(gameApplication));
    }

    public void setScreen(STATE nextScreen) {
        gameApplication.setScreen(gameScreens.get(nextScreen));
    }

    public void initPlayScreen(GraphicalGameMap gameMap, PlayerQueue playerQueue) {
        playScreen = new PlayScreen(gameApplication, gameMap, playerQueue);
        this.gameScreens.put(STATE.PLAY, playScreen);
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if (screen != null) screen.dispose();
        }
    }

}
