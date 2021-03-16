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

    PlayScreen playScreen;

    public GameScreenManager(GameApplication gameApplication) {
        this.gameApplication = gameApplication;
        initializeGameScreens();
        setScreen(STATE.MENU);
    }

    private void initializeGameScreens() {
        this.gameScreens = new HashMap<>();
        this.gameScreens.put(STATE.MENU, new MainMenuScreen(gameApplication));
        playScreen = new PlayScreen(gameApplication);
        this.gameScreens.put(STATE.PLAY, playScreen);
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

    public PlayScreen getGameScreen() {
        return playScreen;
    }

}
