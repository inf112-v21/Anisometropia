package map;

import actor.Player;
import assets.Wall;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.awt.*;
import java.util.Arrays;

public class TextualGameMap extends GameMap {

    final int FLAG_1_ID = 55;
    Point[] flagPositions = new Point[4];

    int width;
    int height;
    int[][] gridArray;

    public TextualGameMap(int width, int height) {
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
        this.gridArray[2][2] = 39; // Lasers - Horizontal
        this.gridArray[1][7] = Wall.wallDown; //wall

        flagPositions[0] = new Point(3,3);
        flagPositions[1] = new Point(width-1,height-1);
        flagPositions[2] = new Point(width-1,height-2);
        flagPositions[3] = new Point(width-2,height-2);
    }

    public void setPlayerPosition(int x, int y, Player player) {
        this.gridArray[y][x] = 1;
    }

    public void setValue(int y, int x, int value) {
        this.gridArray[y][x] = value;
    }

    public int getValue(int y, int x) {
        return this.gridArray[y][x];
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
        return this.gridArray[x][y] == 6 || this.gridArray[x][y] == 90 || this.gridArray[x][y] == 92;
    }

    public void update() {
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
        return false;
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
        return false;
    }

    @Override
    public boolean isThereRepairStationOnThisPosition(int x, int y) {
        return false;
    }

    @Override
    public boolean isTherePusherOnThisPosition(int x, int y) {
        return false;
    }

    @Override
    public int getPlayerLayerID(int x, int y) {
        return 0;
    }


}

