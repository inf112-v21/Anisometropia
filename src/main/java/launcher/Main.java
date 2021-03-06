package launcher;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static screens.PlayScreen.SCREEN_HEIGHT;
import static screens.PlayScreen.SCREEN_WIDTH;

public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("RoboRally");
        config.setIdleFPS(60);
        config.useVsync(true);
        config.setResizable(false);
        config.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);

        new Lwjgl3Application(new GameApplication(), config);
    }
}