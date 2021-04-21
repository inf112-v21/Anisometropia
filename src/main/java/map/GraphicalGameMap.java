package map;

import actor.Player;
import assets.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

import java.awt.*;

import static screens.PlayScreen.ASSETS_IMAGE_SIZE;
import static screens.PlayScreen.PIXEL_SCALE_FOR_ASSETS;

public class GraphicalGameMap extends GameMap {
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    TiledMapTileLayer boardLayer, laserLayer, assetLayer, playerLayer, playerShootLaser;

    private int Hole_YellowBoarder = 91 , Hole_Black = 92, Hole_grey = 6, Hole_Fire1 = 89, Hole_Fire2 = 90, Hole_YellowBoarder1 = 105,
                Hole_YellowBoarder2 = 106, Hole_YellowBoarder3 = 107, Hole_YellowBoarder4 = 108, Hole_YellowBoarder5 = 109,
                Hole_YellowBoarder6 = 110, Hole_YellowBoarder7 = 113, Hole_YellowBoarder8 = 114, Hole_YellowBoarder9 = 115,
                Hole_YellowBoarder10 = 116, Hole_YellowBoarder11 = 117, Hole_YellowBoarder12 = 118;

    private int StartPosID_1 = 121, StartPosID_2 = 122, StartPosID_3 = 123, StartPosID_4 = 124, StartPosID_5 = 125, StartPosID_6 = 126, StartPosID_7 = 127, StartPosID_8 = 128;

    Point[] spawnPositions = new Point[8];
    Point[] flagPositions = new Point[4];

    TextureRegion[][] playerImages;
    TiledMapTileLayer.Cell[]player1Cells = new TiledMapTileLayer.Cell[6];
    TiledMapTileLayer.Cell[]player2Cells = new TiledMapTileLayer.Cell[6];
    TiledMapTileLayer.Cell[]player3Cells = new TiledMapTileLayer.Cell[6];
    TiledMapTileLayer.Cell[]player4Cells = new TiledMapTileLayer.Cell[6];
    TiledMapTileLayer.Cell[][] playerCells = new TiledMapTileLayer.Cell[4][6];

    Wall wall;

    public GraphicalGameMap(String map) {
        tiledMap = new TmxMapLoader().load(map+".tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, PIXEL_SCALE_FOR_ASSETS  / ASSETS_IMAGE_SIZE);

        boardLayer = (TiledMapTileLayer) tiledMap.getLayers().get("BoardLayer");
        playerLayer = (TiledMapTileLayer) tiledMap.getLayers().get("PlayerLayer");
        assetLayer = (TiledMapTileLayer) tiledMap.getLayers().get("AssetLayer");
        laserLayer = (TiledMapTileLayer) tiledMap.getLayers().get("LaserLayer");
        playerShootLaser = (TiledMapTileLayer) tiledMap.getLayers().get("PlayerShootLaser");

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

        wall = new Wall(this);
        findSpawnPositions();
        findFlagPositions();
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
            return (tileID == Flag.FLAG_1_ID) || (tileID == Flag.FLAG_2_ID) ||
                    (tileID == Flag.FLAG_3_ID) || (tileID == Flag.FLAG_4_ID);
        }
        return false;
    }

    public boolean isThereHoleOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x, y) != null) {
            int tileID = getAssetLayerID(x, y);
            return (tileID == Hole_Black) || (tileID == Hole_Fire1) || (tileID == Hole_Fire2) ||
                    (tileID == Hole_grey) || (tileID == Hole_YellowBoarder);
        }
        return false;
    }

    public boolean isThereLaserBeamsOnThisPosition(int x, int y){
        if (laserLayer.getCell(x, y) != null){
            int tileID = getLaserLayerID(x,y);
            return  (tileID == DamageAssets.laserBeamHorizontal )     || (tileID == DamageAssets.laserBeamVertical)      ||
                    (tileID == DamageAssets.doubleLaserBeamHorizontal)|| (tileID == DamageAssets.doubleLaserBeamVertical)||
                    (tileID == DamageAssets.laserBeamCrossing)        || (tileID == DamageAssets.doubleLaserBeamCrossing);
        }

        return false;
    }

    public boolean isThereYellowConveyorOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x, y) != null) {
            int tileID = getAssetLayerID(x, y);
            return  (tileID == MovingAssets.yConveyorBeltID_Down) || (tileID == MovingAssets.yConveyorBeltID_Left) ||
                    (tileID == MovingAssets.yConveyorBeltID_Up)   || (tileID == MovingAssets.yConveyorBeltID_Right)||
                    (tileID == MovingAssets.yConveyorBeltID_DownLeft) || (tileID == MovingAssets.yConveyorBeltID_DownRight) ||
                    (tileID == MovingAssets.yConveyorBeltID_LeftDown) || (tileID == MovingAssets.yConveyorBeltID_LeftUp) ||
                    (tileID == MovingAssets.yConveyorBeltID_RightDown) || (tileID == MovingAssets.yConveyorBeltID_RightUp) ||
                    (tileID == MovingAssets.yConveyorBeltID_UpLeft) || (tileID == MovingAssets.yConveyorBeltID_UpRight);
        }
        return false;
    }

    @Override
    public boolean isThereBlueConveyorOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x,y) != null){
            int tileID =getAssetLayerID(x, y);
            return  (tileID == MovingAssets.bConveyorBeltID_Down) || (tileID == MovingAssets.bConveyorBeltID_Left) ||
                    (tileID == MovingAssets.bConveyorBeltID_Up)   || (tileID == MovingAssets.bConveyorBeltID_Right)||
                    (tileID == MovingAssets.bConveyorBeltID_DownLeft) || (tileID == MovingAssets.bConveyorBeltID_DownRight) ||
                    (tileID == MovingAssets.bConveyorBeltID_LeftDown) || (tileID == MovingAssets.bConveyorBeltID_LeftUp) ||
                    (tileID == MovingAssets.bConveyorBeltID_RightDown) || (tileID == MovingAssets.bConveyorBeltID_RightUp) ||
                    (tileID == MovingAssets.bConveyorBeltID_UpLeft) || (tileID == MovingAssets.bConveyorBeltID_UpRight);
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

    @Override
    public boolean isThereGearOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x, y) != null){
            int tileID = getAssetLayerID(x, y);

            return (tileID == MovingAssets.gearRotatingLeft) || (tileID == MovingAssets.gearRotatingRight);
        }
        return false;
    }

    @Override
    public boolean isThereRepairStationOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x, y) != null){
            int tileID = getAssetLayerID(x, y);

            return (tileID == DamageAssets.wrench) || (tileID == DamageAssets.doubleWrench);
        }
        return false;
    }

    @Override
    public boolean isTherePusherOnThisPosition(int x, int y) {
        if (assetLayer.getCell(x, y) != null){
            int tileID = getAssetLayerID(x, y);
            return  (tileID == MovingAssets.pusherDown) || (tileID == MovingAssets.pusherLeft) ||
                    (tileID == MovingAssets.pusherUp) || (tileID == MovingAssets.pusherRight);
        }
        return false;
    }

    @Override
    public boolean isTherePlayerOnThisPosition(int x, int y){
        return playerLayer.getCell(x, y) != null;
    }

    public int getAssetLayerID(int x, int y) {
        return assetLayer.getCell(x, y).getTile().getId();
    }

    public int getLaserLayerID(int x, int y){
        return laserLayer.getCell(x, y).getTile().getId();
    }

    public int getPlayerLayerID(int x, int y){
        return playerLayer.getCell(x, y).getTile().getId();
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

    public void findSpawnPositions() {
        for (int width = 0; width < getWidth(); width++) {
            for (int height = 0; height < getHeight(); height++) {
                if (assetLayer.getCell(width, height) != null) {
                    if (getAssetLayerID(width, height) == (StartPosID_1)) {
                        spawnPositions[0] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (StartPosID_2)) {
                        spawnPositions[1] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (StartPosID_3)) {
                        spawnPositions[2] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (StartPosID_4)) {
                        spawnPositions[3] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (StartPosID_5)) {
                        spawnPositions[4] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (StartPosID_6)) {
                        spawnPositions[5] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (StartPosID_7)) {
                        spawnPositions[6] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (StartPosID_8)) {
                        spawnPositions[7] = new Point(width, height);
                    }
                }
            }
        }
    }

    private void findFlagPositions() {
        for (int width = 0; width < getWidth(); width++) {
            for (int height = 0; height < getHeight(); height++) {
                if (assetLayer.getCell(width, height) != null) {
                    if (getAssetLayerID(width, height) == (Flag.FLAG_1_ID)) {
                        flagPositions[0] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (Flag.FLAG_2_ID)) {
                        flagPositions[1] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (Flag.FLAG_3_ID)) {
                        flagPositions[2] = new Point(width, height);
                    }
                    if (getAssetLayerID(width, height) == (Flag.FLAG_4_ID)) {
                        flagPositions[3] = new Point(width, height);
                    }
                }
            }
        }
    }

    public Point getSpawnPoint(int playerIndex) {
        return spawnPositions[playerIndex];
    }

    public Point getFlagPosition(int flagNumber) {
        return flagPositions[flagNumber];
    }

    public void dispose() {
        tiledMap.dispose();
        orthogonalTiledMapRenderer.dispose();
    }


}
