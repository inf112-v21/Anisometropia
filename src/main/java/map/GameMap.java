package map;


import actor.Player;

public abstract class GameMap {

    public abstract boolean isThereFlagHere(int x, int y);

    public abstract int getAssetLayerID(int x, int y);

    public abstract boolean isThereHoleOnThisPosition(int x, int y);

    public abstract void setPlayerPosition(int x, int y, Player player);

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void setToNull(int x, int y);

    public abstract boolean isThereConveyorOnThisPosition(int x, int y);
}