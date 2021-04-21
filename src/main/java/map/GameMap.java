package map;


import actor.Player;
import assets.Wall;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.awt.*;

public abstract class GameMap {

    public abstract TiledMapTileLayer.Cell getLaserCell(int i);

    public abstract void setCell(int x, int y, String layer, TiledMapTileLayer.Cell cell);

    public abstract boolean isThereFlagHere(int x, int y);

    public abstract int getAssetLayerID(int x, int y);

    public abstract int getLaserLayerID(int x, int y);

    public abstract boolean isThereLaserBeamsOnThisPosition(int x, int y);

    public abstract boolean isThereHoleOnThisPosition(int x, int y);

    public abstract void setPlayerPosition(int x, int y, Player player);

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void setToNull(int x, int y);

    public abstract boolean isTherePlayerOnThisPosition(int x, int y);

    public abstract boolean isThereYellowConveyorOnThisPosition(int x, int y);

    public abstract boolean isThereBlueConveyorOnThisPosition(int x, int y);

    public abstract boolean isThereWallOnThisPosition(int x, int y);

    public abstract boolean isThereGearOnThisPosition(int x, int y);

    public abstract boolean isThereRepairStationOnThisPosition(int x,int y);

    public abstract boolean isTherePusherOnThisPosition(int x, int y);

    public abstract int getPlayerLayerID(int x, int y);

    public abstract Wall getWall();

    public abstract Point getFlagPosition(int flagNumber);
}
