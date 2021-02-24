package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import org.lwjgl.opengl.GL20;

public class GameScreen extends ApplicationAdapter implements InputProcessor {
    public static final int SCREEN_WIDTH = 1696;
    public static final int SCREEN_HEIGHT = 960;
    public static final int GAMEBOARD_PLACEMENT_X = -32;
    public static final int GAMEBOARD_PLACEMENT_Y = -160;
    public static final float ASSETS_IMAGE_SIZE = 300f;
    public static final float PIXEL_SCALE_FOR_ASSETS = 64f;

    SpriteBatch batch;
    BitmapFont smallFont, largeFont;
    OrthographicCamera camera;

    GraphicalGameMap gameMap;
    GameLogic gameLogic;
    Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        smallFont = new BitmapFont();
        smallFont.setColor(Color.BLACK);
        smallFont.getData().setScale(1.5f);
        largeFont = new BitmapFont();
        largeFont.setColor(Color.WHITE);
        largeFont.getData().setScale(4f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(GAMEBOARD_PLACEMENT_X, GAMEBOARD_PLACEMENT_Y);
        camera.update();

        gameMap = new GraphicalGameMap();
        gameLogic = new GameLogic(gameMap);
        player = gameLogic.getCurrentPlayer();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.7f,0.6f,0.4f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // prints out the coordinates of the position clicked
        if(Gdx.input.justTouched()) {
            Vector3 clickPosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println("(" + Math.round(clickPosition.x) + ", " + Math.round(clickPosition.y) + ")");
        }

        gameLogic.update();
        gameMap.render(camera, batch);

        batch.begin();

        if(GameLogic.gameOver) largeFont.draw(batch, GameLogic.gameMessage, 96, 512);
        smallFont.draw(batch, "WASD:     move\n" +
                "X:              rotate player clockwise\n" +
                "C:              move player forwards\n" +
                "R:              respawn\n" +
                "ESCAPE:  exit", 0, -16);

        batch.end();
    }

    @Override
    public void dispose() {
        gameMap.dispose();
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE) Gdx.app.exit();
        if(keycode == Input.Keys.R) gameLogic.gameReset();
        if (!GameLogic.gameOver) {
            switch (keycode) {
                case Input.Keys.UP: case Input.Keys.W:
                    gameLogic.getCurrentPlayer().move(0, 1);
                    gameLogic.playerQueue.next();
                    break;
                case Input.Keys.DOWN: case Input.Keys.S:
                    gameLogic.getCurrentPlayer().move(0, -1);
                    gameLogic.playerQueue.next();
                    break;
                case Input.Keys.LEFT: case Input.Keys.A:
                    gameLogic.getCurrentPlayer().move(-1, 0);
                    gameLogic.playerQueue.next();
                    break;
                case Input.Keys.RIGHT: case Input.Keys.D:
                    gameLogic.getCurrentPlayer().move(1, 0);
                    gameLogic.playerQueue.next();
                    break;
                case Input.Keys.X:
                    gameLogic.getCurrentPlayer().rotate(1);
                    break;
                case Input.Keys.C:
                    gameLogic.getCurrentPlayer().moveByDirection(1);
                    break;
            }
        }
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