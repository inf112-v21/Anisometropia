package screens;

import launcher.GameApplication;

import java.util.HashMap;

public class GameScreenManager {
    public final GameApplication gameApplication;
    private HashMap<STATE, AbstractScreen> gameScreens;
    public enum STATE {
        MENU,
        PLAY,
        LOCAL_SETUP,
        ON_NET_SETUP,
        CREDITS
    }

    GameScreen gameScreen;

    public GameScreenManager(GameApplication gameApplication) {
        this.gameApplication = gameApplication;
        initializeGameScreen();
        setScreen(STATE.MENU);
    }

    private void initializeGameScreen() {
        this.gameScreens = new HashMap<>();
        this.gameScreens.put(STATE.MENU, new MainMenuScreen(gameApplication));
        gameScreen = new GameScreen(gameApplication);
        this.gameScreens.put(STATE.PLAY, gameScreen);
        this.gameScreens.put(STATE.LOCAL_SETUP, new LocalSetupScreen(gameApplication));
    }

    public void setScreen(STATE nextScreen) {
        gameApplication.setScreen(gameScreens.get(nextScreen));
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if (screen != null) screen.dispose();
        }
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

}
