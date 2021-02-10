package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
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


public class Board extends InputAdapter implements ApplicationListener  {
    private SpriteBatch batch;
    private BitmapFont font;

    //Variables below deals with creating the map, and the different layers of the map.
    private TiledMap map;
    private TiledMapTileLayer boardLayer, playerLayer, holeLayer, flagLayer; //remember to add more layers here if added to gameboard
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private int mapSizeX = 5;
    private int mapSizeY = 5;
    static float cameraHeight = (float) 5; //Can have values 0-5, but has to be set to 5 to work. Strange?

    //Variables below deals with the player
    private TiledMapTileLayer.Cell playerCell, playerWonCell, playerDiedCell;
    private Vector2 playerPosition;
    private int playerSpawnX = 0;
    private int playerSpawnY = 0;

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
        //wallLayer = (TiledMapTileLayer) map.getLayers().get("Wall");
        //Pits
        //gears : Clockwise gear
        //        Counterclockwise gear
        //The priority antenna
        //Conveyor Belts: Green conveyor belt (moves 1 space)
        //                rotating conveyor belt
        //                rotating merge conveyor belt
        //                rotating double merge conveyor belt
        //
        //                Blue conveyor belt (moves 2 spaces)
        //                rotating conveyor belt
        //                rotating merge conveyor belt
        //                rotating double merge conveyor belt
        //Crushers: Active crusher
        //Lasers: 1, 2 & 3 laser-beams.
        //Energy space
        //Checkpoints
        //Start Space




        //Initializing camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, mapSizeX, mapSizeY);
        camera.viewportHeight = cameraHeight;
        camera.update();
        renderer = new OrthogonalTiledMapRenderer(map, (float)(0.00333));
        renderer.setView(camera);

        //Initializing player variables
        //But first, loading the player image from our assets folder and splitting it to three images.
        TextureRegion[][] playerImages  = TextureRegion.split(new Texture("assets/player.png"), 300, 300);
        //Initializing player variables with their respective images (texture regions)
        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][0]));
        playerWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][2]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][1]));
        playerPosition = new Vector2(playerSpawnX,playerSpawnY);

        //Register the input processor to enable pressing keys to move player.
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.UP && playerPosition.y <= 3) {
            playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, null); //important to clear the cell we are on before moving.
            playerPosition.y += 1;
        }
        if(keycode == Input.Keys.DOWN && playerPosition.y > 0) {
            playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, null);
            playerPosition.y -= 1;
        }
        if(keycode == Input.Keys.RIGHT && playerPosition.x <= 3){
            playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, null);
            playerPosition.x += 1;
        }
        if (keycode == Input.Keys.LEFT && playerPosition.x > 0) {
            playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, null);
            playerPosition.x -= 1;
        }
        return super.keyDown(keycode);
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
        //to display the gameboard
        renderer.render();
        //to display player 
        if(checkWin()) {
            playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerWonCell);
        } else if (checkLoss()) {
            playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerDiedCell);
        }
        else {
            playerLayer.setCell((int) playerPosition.x, (int) playerPosition.y, playerCell);
        }
    }

    public boolean checkWin() {
        return flagLayer.getCell((int) playerPosition.x, (int) playerPosition.y) != null;
    }

    public boolean checkLoss() {
         return holeLayer.getCell((int) playerPosition.x, (int) playerPosition.y) != null;
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
