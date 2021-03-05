package screens;

import actor.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import logic.GameLogic;
import map.GraphicalGameMap;
import org.lwjgl.opengl.GL20;

public class GameScreen extends ApplicationAdapter implements InputProcessor {
    public static final int SCREEN_WIDTH = 1440;
    public static final int SCREEN_HEIGHT = 832;
    public static final int GAMEBOARD_PLACEMENT_X = -32;
    public static final int GAMEBOARD_PLACEMENT_Y = -256;
    public static final float ASSETS_IMAGE_SIZE = 300f;
    public static final float PIXEL_SCALE_FOR_ASSETS = 48f;

    SpriteBatch batch;
    OrthographicCamera camera;
    OrthographicCamera controlCamera;

    GraphicalGameMap gameMap;
    GameLogic gameLogic;
    Player player;

    ControlScreen controlScreen;

    @Override
    public void create() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(GAMEBOARD_PLACEMENT_X, GAMEBOARD_PLACEMENT_Y);
        camera.update();

        controlCamera = new OrthographicCamera();
        controlCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        controlCamera.translate(-32, -32);
        controlCamera.update();

        gameMap = new GraphicalGameMap();
        gameLogic = new GameLogic(gameMap);
        player = gameLogic.getCurrentPlayer();

        controlScreen = new ControlScreen(gameLogic);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.7f,0.6f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameLogic.update();

        controlScreen.render(controlCamera);

        gameMap.render(camera, batch);

        batch.begin();

        batch.end();
    }

    @Override
    public void dispose() {
        gameMap.dispose();
        controlScreen.dispose();
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE) Gdx.app.exit();
        if(keycode == Input.Keys.R) gameLogic.gameReset();
        return false;
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