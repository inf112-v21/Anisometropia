package map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import actor.Player;

import static screens.GameScreen.ASSETS_IMAGE_SIZE;
import static screens.GameScreen.PIXEL_SCALE_FOR_ASSETS;

public class GraphicalGameMap extends GameMap {
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    TiledMapTileLayer boardLayer, laserLayer, assetLayer, playerLayer;

    TextureRegion[][] playerImages;
    TiledMapTileLayer.Cell playerCellNorth, playerCellEast, playerCellSouth, playerCellWest, playerWonCell, playerDiedCell;

    public GraphicalGameMap() {
        tiledMap = new TmxMapLoader().load("gameboard2.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, PIXEL_SCALE_FOR_ASSETS  / ASSETS_IMAGE_SIZE);

        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BoardLayer");
        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("PlayerLayer");
        assetLayer = (TiledMapTileLayer) tiledMap.getLayers().get("AssetLayer");
        laserLayer = (TiledMapTileLayer) tiledMap.getLayers().get("LaserLayer");

        playerImages  = TextureRegion.split(new Texture("player.png"), 300, 300);
        playerCellNorth = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[1][1]));
        playerCellEast = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[1][2]));
        playerCellSouth = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][0]));
        playerCellWest = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[1][0]));
        playerWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][2]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][1]));


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

    public boolean isThereFlagHere(int x, int y) {
        if (assetLayer.getCell(x, y) != null) {
            int tileID = getAssetLayerID(x, y);
            return (tileID == 55) || (tileID == 63) || (tileID == 71) || (tileID == 79);
        }
        return false;
    }

    public boolean isThereHoleOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x, y) != null) {
            int tileID = getAssetLayerID(x, y);
            return (tileID == 91) || (tileID == 92);
        }
        return false;
    }

    public boolean isThereLaserBeamsOnThisPosition(int x, int y){
        if (laserLayer.getCell(x, y) != null){
            int tileID = getLaserLayerID(x,y);
            return  (tileID == 38)  || (tileID == 46)  || (tileID == 39) ||
                    (tileID == 101) || (tileID == 102) || (tileID == 100);
        }

        return false;
    }

    public boolean isThereConveyorOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x, y) != null) {
            int tileID = getAssetLayerID(x, y);
            return  (tileID == 49) || (tileID == 50) || (tileID == 51) || (tileID == 52) ||
                    (tileID == 57) || (tileID == 58) || (tileID == 59) || (tileID == 60) ||
                    (tileID == 65) || (tileID == 66) || (tileID == 67) || (tileID == 68) ||
                    (tileID == 12) || (tileID == 13) || (tileID == 16) || (tileID == 17) ||
                    (tileID == 18) || (tileID == 19) || (tileID == 20) || (tileID == 21) ||
                    (tileID == 24) || (tileID == 25) || (tileID == 26) || (tileID == 27);
        }
        return false;
    }



    public int getAssetLayerID(int x, int y) {
        return assetLayer.getCell(x, y).getTile().getId();
    }
    public int getLaserLayerID(int x, int y){
        return laserLayer.getCell(x, y).getTile().getId();
    }

    public void setPlayerPosition(int x, int y, Player player) {
        TiledMapTileLayer.Cell cellToBeDisplayed = playerCellNorth;
        if (player.isPlayerDead()) {
            cellToBeDisplayed = playerDiedCell;
        } else if (player.getVictorious()) {
            cellToBeDisplayed = playerWonCell;
        } else {
            switch (player.getDirection()) {
                case (0):
                    cellToBeDisplayed = playerCellNorth;
                    break;
                case (1):
                    cellToBeDisplayed = playerCellEast;
                    break;
                case (2):
                    cellToBeDisplayed = playerCellSouth;
                    break;
                case (3):
                    cellToBeDisplayed = playerCellWest;
                    break;
            }
        }
        setCell(x,y, "PlayerLayer", cellToBeDisplayed);
    }

    public int getWidth() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
    }

    public int getHeight() {
        return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
    }

    public void setToNull(int x, int y) {
        ((TiledMapTileLayer) tiledMap.getLayers().get("PlayerLayer")).setCell(x, y, null);
    }

    public void dispose() {
        tiledMap.dispose();
        orthogonalTiledMapRenderer.dispose();
    }


}
