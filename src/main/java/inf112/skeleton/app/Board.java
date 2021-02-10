package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.ParticleChannels;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;

import java.awt.*;


public class Board extends InputAdapter implements ApplicationListener  {
    private SpriteBatch batch;
    private BitmapFont font;

    //Variables below deals with creating the map, and the different layers of the map.
    private TiledMap map;
    private TiledMapTileLayer boardLayer, playerLayer, holeLayer, flagLayer; //remember to add more layers here if added to gameboard
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    static int mapSizeX = 5, mapSizeY= 5;
    static float cameraHeight = 5f; //Can have values 0-5, but has to be set to 5 to work. Strange?

    //Variables below deals with the player
    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerWonCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private Vector2 playerPos;
    private int posX = 0;
    private int posY = 0;

    public Board() {
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        //Initializing map, and layer variables.
        map = new TmxMapLoader().load("assets/gameboard.tmx");
        boardLayer = (TiledMapTileLayer) map.getLayers().get("assets/gameboard.tmx");
        playerLayer = (TiledMapTileLayer) map.getLayers().get("Player");
        holeLayer = (TiledMapTileLayer) map.getLayers().get("Hole");
        flagLayer =  (TiledMapTileLayer) map.getLayers().get("Flag");

        //Initializing camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapSizeX, mapSizeY);
        camera.viewportHeight = cameraHeight;
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map, 0.00333f);
        renderer.setView(camera);

        //Initializing player variables
        //But first, loading the player image from our assets folder and splitting it to three images.
        TextureRegion playerImages [][] = TextureRegion.split(new Texture("assets/player.png"), 300, 300);
        //Initializing player variables with their respective images (texture regions)
        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][0]));
        playerWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][2]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][1]));
        playerPos = new Vector2(posX,posY);

        //Register the input processor to enable pressing keys to move player.
        Gdx.input.setInputProcessor(this);

    }

    public void update() {
        if(checkWin()) {
            playerLayer.setCell(posX,posY, playerWonCell);
        } else if (checkLoss()) {
            playerLayer.setCell(posX,posY, playerDiedCell);
        }
        else {
            playerLayer.setCell(posX,posY, playerCell);
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        // to update game logic
        update();
        //to display the gameboard
        renderer.render();
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.UP && posY <= 3) {
            playerLayer.setCell(posX, posY, null); //important to clear the cell we are on before moving.
            posY += 1;
        }
        if(keycode == Input.Keys.DOWN && posY > 0) {
            playerLayer.setCell(posX, posY, null);
            posY -= 1;
        }
        if(keycode == Input.Keys.RIGHT && posX <= 3){
            playerLayer.setCell(posX, posY, null);
            posX += 1;
        }
        if (keycode == Input.Keys.LEFT && posX > 0) {
            playerLayer.setCell(posX, posY, null);
            posX -= 1;
        }
        return super.keyDown(keycode);
    }

    public boolean checkWin() {
        return flagLayer.getCell(posX,posY) != null;
    }

    public boolean checkLoss() {
         return holeLayer.getCell(posX,posY) != null;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        renderer.dispose();
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
