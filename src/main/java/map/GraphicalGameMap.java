package map;

import actor.Player;
import assets.ConveyorBelts;
import assets.Wall;
import assets.Laser;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import static screens.PlayScreen.ASSETS_IMAGE_SIZE;
import static screens.PlayScreen.PIXEL_SCALE_FOR_ASSETS;

public class GraphicalGameMap extends GameMap {
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    TiledMapTileLayer boardLayer, laserLayer, assetLayer, playerLayer;

    TextureRegion[][] playerImages;
    TiledMapTileLayer.Cell[]player1Cells = new TiledMapTileLayer.Cell[6];
    TiledMapTileLayer.Cell[]player2Cells = new TiledMapTileLayer.Cell[6];
    TiledMapTileLayer.Cell[]player3Cells = new TiledMapTileLayer.Cell[6];
    TiledMapTileLayer.Cell[]player4Cells = new TiledMapTileLayer.Cell[6];
    TiledMapTileLayer.Cell[][] playerCells = new TiledMapTileLayer.Cell[4][6];

    Wall wall;

    public GraphicalGameMap() {
        tiledMap = new TmxMapLoader().load("gameboard2.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, PIXEL_SCALE_FOR_ASSETS  / ASSETS_IMAGE_SIZE);

        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BoardLayer");
        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("PlayerLayer");
        assetLayer = (TiledMapTileLayer) tiledMap.getLayers().get("AssetLayer");
        laserLayer = (TiledMapTileLayer) tiledMap.getLayers().get("LaserLayer");

        playerImages  = TextureRegion.split(new Texture("charactersSpriteSheet300.png"), 300, 300);

        player1Cells[0] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[1][1])); // north
        player1Cells[1] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][1])); // east
        player1Cells[2] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][0])); // south
        player1Cells[3] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[1][0])); // west
        player1Cells[4] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[2][0])); // won
        player1Cells[5] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[2][1])); // died

        player2Cells[0] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[1][3])); // north
        player2Cells[1] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][3])); // east
        player2Cells[2] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][2])); // south
        player2Cells[3] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[1][2])); // west
        player2Cells[4] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[2][2])); // won
        player2Cells[5] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[2][3])); // died

        player3Cells[0] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[4][1])); // north
        player3Cells[1] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[3][1])); // east
        player3Cells[2] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[3][0])); // south
        player3Cells[3] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[4][0])); // west
        player3Cells[4] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[5][0])); // won
        player3Cells[5] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[5][1])); // died

        player4Cells[0] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[4][3])); // north
        player4Cells[1] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[3][3])); // east
        player4Cells[2] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[3][2])); // south
        player4Cells[3] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[4][2])); // west
        player4Cells[4] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[5][2])); // won
        player4Cells[5] = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[5][3])); // died

        playerCells[0] = player1Cells;
        playerCells[1] = player2Cells;
        playerCells[2] = player3Cells;
        playerCells[3] = player4Cells;

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

        wall = new Wall(this);
        // just testing
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
            return (tileID == 91) || (tileID == 92) || (tileID == 6);
        }
        return false;
    }

    public boolean isThereLaserBeamsOnThisPosition(int x, int y){
        if (laserLayer.getCell(x, y) != null){
            int tileID = getLaserLayerID(x,y);
            return  (tileID == Laser.laserBeamHorizontal )     || (tileID == Laser.laserBeamVertical)      ||
                    (tileID == Laser.doubleLaserBeamHorizontal)|| (tileID == Laser.doubleLaserBeamVertical)||
                    (tileID == Laser.laserBeamCrossing)        || (tileID == Laser.doubleLaserBeamCrossing);
        }

        return false;
    }

    public boolean isThereYellowConveyorOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x, y) != null) {
            int tileID = getAssetLayerID(x, y);
            return  (tileID == ConveyorBelts.yConveyorBeltID_Down) || (tileID == ConveyorBelts.yConveyorBeltID_Left) ||
                    (tileID == ConveyorBelts.yConveyorBeltID_Up)   || (tileID == ConveyorBelts.yConveyorBeltID_Right)||
                    (tileID == ConveyorBelts.yConveyorBeltID_DownLeft) || (tileID == ConveyorBelts.yConveyorBeltID_DownRight) ||
                    (tileID == ConveyorBelts.yConveyorBeltID_LeftDown) || (tileID == ConveyorBelts.yConveyorBeltID_LeftUp) ||
                    (tileID == ConveyorBelts.yConveyorBeltID_RightDown) || (tileID == ConveyorBelts.yConveyorBeltID_RightUp) ||
                    (tileID == ConveyorBelts.yConveyorBeltID_UpLeft) || (tileID == ConveyorBelts.yConveyorBeltID_UpRight);
        }
        return false;
    }

    @Override
    public boolean isThereBlueConveyorOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x,y) != null){
            int tileID =getAssetLayerID(x, y);
            return  (tileID == ConveyorBelts.bConveyorBeltID_Down) || (tileID == ConveyorBelts.bConveyorBeltID_Left) ||
                    (tileID == ConveyorBelts.bConveyorBeltID_Up)   || (tileID == ConveyorBelts.bConveyorBeltID_Right)||
                    (tileID == ConveyorBelts.bConveyorBeltID_DownLeft) || (tileID == ConveyorBelts.bConveyorBeltID_DownRight) ||
                    (tileID == ConveyorBelts.bConveyorBeltID_LeftDown) || (tileID == ConveyorBelts.bConveyorBeltID_LeftUp) ||
                    (tileID == ConveyorBelts.bConveyorBeltID_RightDown) || (tileID == ConveyorBelts.bConveyorBeltID_RightUp) ||
                    (tileID == ConveyorBelts.bConveyorBeltID_UpLeft) || (tileID == ConveyorBelts.bConveyorBeltID_UpRight);
        }
        return false;
    }

    @Override
    public boolean isThereWallOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x, y) != null) {
            int tileID = getAssetLayerID(x, y);

            return  (tileID == Wall.wallDown)  || (tileID == Wall.laserWallDown)  || (tileID == Wall.doubleLaserWallDown)  ||
                    (tileID == Wall.wallUp)    || (tileID == Wall.laserWallUp)    || (tileID == Wall.doubleLaserWallUp)    ||
                    (tileID == Wall.wallLeft)  || (tileID == Wall.laserWallLeft)  || (tileID == Wall.doubleLaserWallLeft)  ||
                    (tileID == Wall.wallRight) || (tileID == Wall.laserWallRight) || (tileID == Wall.doubleLaserWallRight) ||

                    (tileID == Wall.wallUpLeft)   || (tileID == Wall.wallUpRight) ||
                    (tileID == Wall.wallDownLeft) || (tileID == Wall.wallDownRight);
        }

        return false;
    }

    @Override
    public Wall getWall() {
        return wall;
    }

    public int getAssetLayerID(int x, int y) {
        return assetLayer.getCell(x, y).getTile().getId();
    }

    public int getLaserLayerID(int x, int y){
        return laserLayer.getCell(x, y).getTile().getId();
    }

    public void setPlayerPosition(int x, int y, Player player) {
        TiledMapTileLayer.Cell cellToBeDisplayed = playerCells[player.getCharacterID()][0];
        if (player.isPlayerDead()) {
            cellToBeDisplayed =  playerCells[player.getCharacterID()][5];
        } else if (player.getVictorious()) {
            cellToBeDisplayed =  playerCells[player.getCharacterID()][4];
        } else {
            switch (player.getDirection()) {
                case (0):
                    cellToBeDisplayed =  playerCells[player.getCharacterID()][0];
                    break;
                case (1):
                    cellToBeDisplayed =  playerCells[player.getCharacterID()][1];
                    break;
                case (2):
                    cellToBeDisplayed =  playerCells[player.getCharacterID()][2];
                    break;
                case (3):
                    cellToBeDisplayed =  playerCells[player.getCharacterID()][3];
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
