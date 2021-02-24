package inf112.skeleton.app;


public abstract class GameMap {

    abstract boolean isThereFlagHere(int x, int y);

    abstract int getAssetLayerID(int x, int y);

    abstract boolean isThereHoleOnThisPosition(int x, int y);

    abstract void setPlayerPosition(int x, int y, Player player);

    abstract int getWidth();

    abstract int getHeight();

    abstract void setToNull(int x, int y);

    public abstract boolean isThereConveyorOnThisPosition(int x, int y);
}
