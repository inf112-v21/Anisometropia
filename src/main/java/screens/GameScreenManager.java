package screens;

import actor.Player;
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

        // USE THIS FOR SKIPPING MENUS, AND IMMEDIATELY START A GAME WITH 2 PLAYERS
        GraphicalGameMap gameMap = new GraphicalGameMap();
        PlayerQueue playerQueue = new PlayerQueue();
        playerQueue.add(new Player(2, 2, "PL1", gameMap, 0));
        playerQueue.add(new Player(3, 2, "PL2", gameMap, 1));
        initPlayScreen(gameMap, playerQueue);
        setScreen(GameScreenManager.STATE.PLAY);
        // END
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
