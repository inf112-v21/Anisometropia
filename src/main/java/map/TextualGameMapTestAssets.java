package map;

import actor.Player;
import assets.DamageAssets;
import assets.MovingAssets;
import assets.Wall;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.awt.*;
import java.util.Arrays;

public class TextualGameMapTestAssets extends GameMap{

    final int FLAG_1_ID = 55;
    Point[] flagPositions = new Point[4];

    int width;
    int height;
    int[][] gridArray;

    public TextualGameMapTestAssets(int width, int height) {
        this.width = width;
        this.height = height;
        gridArray = new int[height][width];
        createGrid(gridArray);
    }


    private void createGrid(int[][] gridArray) {
        for (int[] ints : gridArray) {
            Arrays.fill(ints, 0);
        }

        this.gridArray[3][3] = 55; // Flag_1_ID
        this.gridArray[5][5] = 6;  // Hole
        this.gridArray[2][2] = DamageAssets.laserBeamHorizontal;
        this.gridArray[2][3] = DamageAssets.laserBeamVertical;
        this.gridArray[2][4] = DamageAssets.doubleLaserBeamHorizontal;
        this.gridArray[2][5] = DamageAssets.doubleLaserBeamVertical;
        this.gridArray[2][6] = DamageAssets.laserBeamCrossing;
        this.gridArray[2][7] = DamageAssets.doubleLaserBeamCrossing;
        //walls
        this.gridArray[1][11] =Wall.wallUp;
        this.gridArray[1][7] = Wall.wallDown;
        //gears
        this.gridArray[11][11] = MovingAssets.gearRotatingLeft;
        this.gridArray[10][10] = MovingAssets.gearRotatingRight;
        //pushers
        this.gridArray[10][9] = MovingAssets.pusherDown;
        this.gridArray[10][8] = MovingAssets.pusherUp;
        this.gridArray[10][7] = MovingAssets.pusherLeft;
        this.gridArray[10][6] = MovingAssets.pusherRight;
        //wrench
        this.gridArray[5][5] = DamageAssets.wrench;
        this.gridArray[5][6] = DamageAssets.doubleWrench;


        flagPositions[0] = new Point(3,3);
        flagPositions[1] = new Point(width-1,height-1);
        flagPositions[2] = new Point(width-1,height-2);
        flagPositions[3] = new Point(width-2,height-2);
    }

    public void setPlayerPosition(int x, int y, Player player) {
        this.gridArray[y][x] = 1;
    }

    @Override
    public TiledMapTileLayer.Cell getLaserCell(int i) {
        return null;
    }

    @Override
    public void setCell(int x, int y, String layer, TiledMapTileLayer.Cell cell) {

    }

    public boolean isThereFlagHere(int x, int y) {
        return this.gridArray[y][x] == 55;
    }

    public int getAssetLayerID(int x, int y) {
        return this.gridArray[y][x]; // returns the general ID, not Flag ID... needs to be fixed
    }

    @Override
    public int getLaserLayerID(int x, int y) {
        return this.gridArray[y][x];
    }

    public boolean isThereHoleOnThisPosition(int x, int y){
        return this.gridArray[y][x] == 6 || this.gridArray[y][x] == 90 || this.gridArray[y][x] == 92;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setToNull(int x, int y) {
        this.gridArray[y][x] = 0;
    }

    @Override
    public boolean isThereYellowConveyorOnThisPosition(int x, int y) {
        return false;
    }

    @Override
    public boolean isThereBlueConveyorOnThisPosition(int x, int y) {
        return false;
    }

    @Override
    public boolean isThereWallOnThisPosition(int x, int y) {
        return false;
    }

    @Override
    public boolean isThereLaserBeamsOnThisPosition(int x, int y) {
        return  (this.gridArray[y][x] == DamageAssets.laserBeamHorizontal) ||
                (this.gridArray[y][x] == DamageAssets.laserBeamVertical)||
                (this.gridArray[y][x] == DamageAssets.doubleLaserBeamHorizontal)||
                (this.gridArray[y][x] == DamageAssets.doubleLaserBeamVertical)||
                (this.gridArray[y][x] == DamageAssets.laserBeamCrossing)||
                (this.gridArray[y][x] == DamageAssets.doubleLaserBeamCrossing);
    }

    @Override
    public boolean isTherePlayerOnThisPosition(int x, int y){
        return false;
    }

    public Wall getWall() {
        return null;
    }

    public Point getFlagPosition(int flagNumber) {
        return flagPositions[flagNumber];
    }

    @Override
    public boolean isThereGearOnThisPosition(int x, int y) {
        return (this.gridArray[y][x] == MovingAssets.gearRotatingLeft) ||
                (this.gridArray[y][x] == MovingAssets.gearRotatingRight);
    }

    @Override
    public boolean isThereRepairStationOnThisPosition(int x, int y) {
        return  (this.gridArray[y][x] == DamageAssets.wrench) ||
                (this.gridArray[y][x] == DamageAssets.doubleWrench);
    }

    @Override
    public boolean isTherePusherOnThisPosition(int x, int y) {
        return (this.gridArray[y][x] == MovingAssets.pusherDown) ||
                (this.gridArray[y][x] == MovingAssets.pusherUp) ||
                (this.gridArray[y][x] == MovingAssets.pusherLeft) ||
                (this.gridArray[y][x] == MovingAssets.pusherRight);
    }

    @Override
    public int getPlayerLayerID(int x, int y) {
        return 0;
    }
}
