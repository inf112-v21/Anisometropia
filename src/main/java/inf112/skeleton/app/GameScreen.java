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
    public static final int SCREEN_WIDTH = 960;
    public static final int SCREEN_HEIGHT = 720;
    public static final int GAMEBOARD_PLACEMENT_X = -32;
    public static final int GAMEBOARD_PLACEMENT_Y = -320;
    public static final float ASSETS_IMAGE_SIZE = 300f;
    public static final float PIXEL_SCALE_FOR_ASSETS = 32f;

    SpriteBatch batch;
    BitmapFont smallFont, largeFont;
    OrthographicCamera camera;

    GameMap gameMap;
    GameLogic gameLogic;
    Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        smallFont = new BitmapFont();
        smallFont.setColor(Color.BLACK);
        smallFont.getData().setScale(1.5f);
        largeFont = new BitmapFont();
        largeFont.setColor(Color.BLACK);
        largeFont.getData().setScale(7f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(GAMEBOARD_PLACEMENT_X, GAMEBOARD_PLACEMENT_Y);
        camera.update();

        gameMap = new GameMap();
        gameLogic = new GameLogic(this);
        player = gameLogic.getPlayer();

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

        // prints out message when the game ends (game won! / game lost!)
        if(GameLogic.gameOver) largeFont.draw(batch, GameLogic.gameMessage, 32, -96);
        // shows player controls
        smallFont.draw(batch, "WASD:     move\nR:              respawn\nESCAPE:  exit", SCREEN_WIDTH / 2f, 64);

        batch.end();
    }

    @Override
    public void dispose() {
        gameMap.dispose();
        batch.dispose();
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE) Gdx.app.exit();
        if(keycode == Input.Keys.R) gameLogic.gameReset();
        if (!GameLogic.gameOver) {
            switch (keycode) {
                case Input.Keys.UP: case Input.Keys.W:
                    gameLogic.getPlayer().move(0, 1);
                    break;
                case Input.Keys.DOWN: case Input.Keys.S:
                    gameLogic.getPlayer().move(0, -1);
                    break;
                case Input.Keys.LEFT: case Input.Keys.A:
                    gameLogic.getPlayer().move(-1, 0);
                    break;
                case Input.Keys.RIGHT: case Input.Keys.D:
                    gameLogic.getPlayer().move(1, 0);
                    break;
                case Input.Keys.X:
                    gameLogic.getPlayer().rotate(1);
                    break;
                case Input.Keys.C:
                    gameLogic.getPlayer().moveByDirection(1);
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
