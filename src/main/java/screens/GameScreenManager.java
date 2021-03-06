package screens;

import actor.AIPlayer;
import actor.Player;
import launcher.GameApplication;
import logic.GameLogic;
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
//        GraphicalGameMap gameMap = new GraphicalGameMap();
//        PlayerQueue playerQueue = new PlayerQueue();
//        playerQueue.add(new Player((int) gameMap.getSpawnPoint(0).getX(), (int) gameMap.getSpawnPoint(0).getY(), "PL1", gameMap, 0));
//        playerQueue.add(new Player((int) gameMap.getSpawnPoint(1).getX(), (int) gameMap.getSpawnPoint(1).getY(), "PL2", gameMap, 1));
//        initPlayScreen(gameMap, playerQueue);
//        setScreen(GameScreenManager.STATE.PLAY);
        // *** END *** USE THIS FOR SKIPPING MENUS *** END ***

        // USE THIS FOR SKIPPING MENUS, AND IMMEDIATELY START A GAME WITH 1 PLAYER AND 1 AI
//        GraphicalGameMap gameMap = new GraphicalGameMap();
//        PlayerQueue playerQueue = new PlayerQueue();
//        playerQueue.add(new Player((int) gameMap.getSpawnPoint(0).getX(), (int) gameMap.getSpawnPoint(0).getY(), "PL1", gameMap, 0));
//        playerQueue.add(new AIPlayer((int) gameMap.getSpawnPoint(1).getX(), (int) gameMap.getSpawnPoint(1).getY(), "PL2 (AI)", gameMap, 1));
//        initPlayScreen(gameMap, playerQueue);
//        setScreen(GameScreenManager.STATE.PLAY);
        // *** END *** USE THIS FOR SKIPPING MENUS *** END ***

    }

    private void initializeGameScreens() {
        this.gameScreens = new HashMap<>();
        this.gameScreens.put(STATE.MENU, new MainMenuScreen(gameApplication));
        this.gameScreens.put(STATE.ON_NET_SETUP, new OnNetSetupScreen(gameApplication));
        this.gameScreens.put(STATE.LOCAL_SETUP, new LocalSetupScreen(gameApplication));
    }

    public void setScreen(STATE nextScreen) {
        gameApplication.setScreen(gameScreens.get(nextScreen));
    }

    public void initPlayScreen(GraphicalGameMap gameMap, GameLogic gameLogic) {
        playScreen = new PlayScreen(gameApplication, gameMap, gameLogic);
        this.gameScreens.put(STATE.PLAY, playScreen);
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if (screen != null) screen.dispose();
        }
    }

}
