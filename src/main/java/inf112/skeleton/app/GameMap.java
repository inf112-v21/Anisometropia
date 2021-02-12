package inf112.skeleton.app;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import static inf112.skeleton.app.GameScreen.ASSETS_IMAGE_SIZE;
import static inf112.skeleton.app.GameScreen.PIXEL_SCALE_FOR_ASSETS;

public class GameMap {
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    TiledMapTileLayer boardLayer, playerLayer, holeLayer, flagLayer, wallLayer;

    public GameMap() {
        tiledMap = new TmxMapLoader().load("gameboard2.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, PIXEL_SCALE_FOR_ASSETS  / ASSETS_IMAGE_SIZE);
        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BoardLayer");
        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("PlayerLayer");
        holeLayer = (TiledMapTileLayer) tiledMap.getLayers().get("HoleLayer");
        flagLayer = (TiledMapTileLayer) tiledMap.getLayers().get("FlagLayer");
        wallLayer = (TiledMapTileLayer) tiledMap.getLayers().get("WallLayer");
        /*
        Pits
        Gears:  - Clockwise gear
                - Counterclockwise gear
        Priority antenna
        Conveyor Belts: - Green conveyor belt (moves 1 space)
                        - rotating conveyor belt
                        - rotating merge conveyor belt
                        - rotating double merge conveyor belt

                        - Blue conveyor belt (moves 2 spaces)
                        - rotating conveyor belt
                        - rotating merge conveyor belt
                        - rotating double merge conveyor belt
        Crushers: Active crusher
        Lasers: 1, 2 & 3 laser-beams.
        Energy space
        Checkpoints
        Start Space
        */
    }

    public void render(OrthographicCamera camera, SpriteBatch batch) {
        orthogonalTiledMapRenderer.setView(camera);
        orthogonalTiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.end();
    }

    public void setCell(int x, int y, String layer, TiledMapTileLayer.Cell cell) {
        ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).setCell(x, y, cell);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public int getWidth() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }

    public int getHeight() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }

    public void dispose() {
        tiledMap.dispose();
    }


}
