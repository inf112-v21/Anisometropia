package assets;

import map.GameMap;

public class Wall {

    public static final int wallRight = 23, wallDown = 29, wallLeft = 30, wallUp = 31,
            wallDownRight = 8, wallUpRight = 16, wallUpLeft = 24, wallDownLeft = 32,
            laserWallDown = 37, laserWallUp = 45, laserWallRight = 46, laserWallLeft = 38,
            doubleLaserWallDown = 87, doubleLaserWallUp = 94, doubleLaserWallRight = 95, doubleLaserWallLeft = 93;

    GameMap gameMap;

    public Wall(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public boolean checkIntoWall(int x, int y, int dx, int dy) {
        if (gameMap.isThereWallOnThisPosition(x+dx, y+dy) ||
                gameMap.isTherePusherOnThisPosition(x +dx, y+dy)) {
            int wallID = gameMap.getAssetLayerID(x + dx, y + dy);
            switch (wallID) {
                case (wallLeft):
                case (laserWallLeft):
                case (doubleLaserWallLeft):
                case (MovingAssets.pusherRight):
                    if (dx == 1 && dy == 0) return false;
                    break;

                case (wallRight):
                case (laserWallRight):
                case (doubleLaserWallRight):
                case (MovingAssets.pusherLeft):
                    if (dx == -1 && dy == 0) return false;
                    break;

                case (wallDown):
                case (laserWallDown):
                case (doubleLaserWallDown):
                case (MovingAssets.pusherUp):
                    if (dx == 0 && dy == 1) return false;
                    break;

                case (wallUp):
                case (laserWallUp):
                case (doubleLaserWallUp):
                case (MovingAssets.pusherDown):
                    if (dx == 0 && dy == -1) return false;
                    break;

                case (wallUpLeft):
                    if ((dx == 0 && dy == -1) || (dx == 1 && dy == 0)) return false;
                    break;

                case (wallUpRight):
                    if ((dx == 0 && dy == -1) || (dx == -1 && dy == 0)) return false;
                    break;

                case (wallDownLeft):
                    if ((dx == 0 && dy == 1) || (dx == 1 && dy == 0)) return false;
                    break;

                case (wallDownRight):
                    if ((dx == 0 && dy == 1) || (dx == -1 && dy == 0)) return false;
                    break;

                default:
                    throw new IllegalStateException("Unexpected wallID: " +wallID+ " x: " +x+ " y: " +y);
            }
        }
        return true;
    }


    public boolean checkOutOfWall(int x, int y, int dx, int dy) {
        if (gameMap.isThereWallOnThisPosition(x, y)) {
            int wallID = gameMap.getAssetLayerID(x, y);
            switch (wallID) {
                case (wallLeft):
                case (laserWallLeft):
                case (doubleLaserWallLeft):
                    if (dx == -1 && dy == 0) return false;
                    break;

                case (wallRight):
                case (laserWallRight):
                case (doubleLaserWallRight):
                    if (dx == 1 && dy == 0) return false;
                    break;

                case (wallDown):
                case (Wall.laserWallDown):
                case (Wall.doubleLaserWallDown):
                    if (dx == 0 && dy == -1) return false;
                    break;

                case (wallUp):
                case (laserWallUp):
                case (doubleLaserWallUp):
                    if (dx == 0 && dy == 1) return false;
                    break;

                case (wallDownRight):
                    if ((dx == 0 && dy == -1) || (dx == 1 && dy == 0)) return false;
                    break;

                case (wallDownLeft):
                    if ((dx == 0 && dy == -1) || (dx == -1 && dy == 0)) return false;
                    break;

                case (wallUpRight):
                    if ((dx == 0 && dy == 1) || (dx == 1 && dy == 0)) return false;
                    break;

                case (wallUpLeft):
                    if ((dx == 0 && dy == 1) || (dx == -1 && dy == 0)) return false;
                    break;


                default:
                    throw new IllegalStateException("Unexpected wallID: " +wallID+ " x: " +x+ " y: " +y);
            }
        }
        return true;
    }

}
