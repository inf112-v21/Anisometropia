package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import launcher.GameApplication;
import logic.GameLogic;
import logic.PlayerQueue;
import map.GraphicalGameMap;

import java.io.IOException;

public class PlayScreen extends AbstractScreen implements InputProcessor {
    public static final int SCREEN_WIDTH = 1440;
    public static final int SCREEN_HEIGHT = 832;
    public static final int GAMEBOARD_PLACEMENT_X = -32;
    public static final int GAMEBOARD_PLACEMENT_Y = -256;
    public static final float ASSETS_IMAGE_SIZE = 300f;
    public static final float PIXEL_SCALE_FOR_ASSETS = 48f;

    launcher.GameApplication gameApplication;

    SpriteBatch batch;
    OrthographicCamera camera;
    OrthographicCamera controlCamera;

    GraphicalGameMap gameMap;
    GameLogic gameLogic;
    ControlScreen controlScreen;

    public PlayScreen(GameApplication gameApplication, GraphicalGameMap gameMap, GameLogic gameLogic) {
        super(gameApplication);
        this.gameApplication = gameApplication;
        batch = gameApplication.spriteBatch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(GAMEBOARD_PLACEMENT_X, GAMEBOARD_PLACEMENT_Y);
        camera.update();

        controlCamera = new OrthographicCamera();
        controlCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        controlCamera.translate(-32, -32);
        controlCamera.update();

        this.gameMap = gameMap;
        this.gameLogic = gameLogic;

        controlScreen = new ControlScreen(gameLogic);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);

        try {
            controlScreen.render(controlCamera);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameMap.render(camera, batch);

        batch.begin();

        batch.end();
    }

    @Override
    public void update(float delta) {
        gameLogic.update();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE) Gdx.app.exit();
        if(keycode == Input.Keys.R) gameLogic.gameReset();
        return false;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    @Override
    public void dispose() {
        gameMap.dispose();
        controlScreen.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}