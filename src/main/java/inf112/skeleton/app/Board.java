package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;

public class Board extends InputAdapter implements ApplicationListener{
    private SpriteBatch batch;
    private BitmapFont font;

    //Variables below deals with creating the map, and the different layers of the map.
    private TiledMap map;
    private TiledMapTileLayer boardLayer, playerLayer, holeLayer, flagLayer; //remember to add more layers here if added to gameboard
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    static int mapSizeX = 5;
    static int mapSizeY= 5;
    static float cameraHeight = (float) 5; //Can have values 0-5, but has to be set to 5 to work Strange?

    //Variables for displaying player.
    private TiledMapTileLayer.Cell playerCell, playerDiedCell, playerWonCell;
    private Vector2 playerPosition;

    public Board() {
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        //Initializing map, and layer variables.
        map = new TmxMapLoader().load("assets/gameboard.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("Board");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapSizeX, mapSizeY);
        camera.viewportHeight = cameraHeight;
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map, (float)(0.00333));
        renderer.setView(camera);

        //Initializing player variables.
        Texture playerTexture = new Texture("assets/player.png");
        TextureRegion[][] playerSplit = new TextureRegion(playerTexture).split(300,300);
        playerCell = new TiledMapTileLayer.Cell();
        playerDiedCell = new TiledMapTileLayer.Cell();
        playerWonCell = new TiledMapTileLayer.Cell();
        playerCell.setTile(new StaticTiledMapTile(playerSplit[0][0]));
        playerDiedCell.setTile(new StaticTiledMapTile(playerSplit[0][1]));
        playerWonCell.setTile(new StaticTiledMapTile(playerSplit[0][2]));
        playerPosition = new Vector2(0,0);

        //Allows for processing keyboard input.
        Gdx.input.setInputProcessor(this);

    }

    //Changes position of player according to keypress, within the bounds of the board.
    //TODO: clear cell before moving.
    @Override
    public boolean keyUp(int keycode) {
        float x = playerPosition.x;
        float y = playerPosition.y;

        if(keycode == 19) {
            playerPosition.set(x, Math.min(y+1, mapSizeY-1));
        }
        else if(keycode == 20) {
            playerPosition.set(x, Math.max(y-1, 0));
        }
        else if(keycode == 21) {
            playerPosition.set(Math.max(x-1, 0), y);
        }
        else if(keycode == 22) {
            playerPosition.set(Math.min(x+1, mapSizeX-1), y);
        }
        playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerCell);
        return true;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        renderer.render();

        //Displays player on board.
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        playerLayer.setCell((int) playerPosition.x,(int) playerPosition.y, playerCell);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
