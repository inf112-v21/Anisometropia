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
        if (gameMap.isThereWallOnThisPosition(x+dx, y+dy)) {
            int wallID = gameMap.getAssetLayerID(x + dx, y + dy);
            switch (wallID) {
                case (Wall.wallLeft):
                case (Wall.laserWallLeft):
                case (Wall.doubleLaserWallLeft):
                    if (dx == 1 && dy == 0) return false;
                    break;

                case (Wall.wallRight):
                case (Wall.laserWallRight):
                case (Wall.doubleLaserWallRight):
                    if (dx == -1 && dy == 0) return false;
                    break;

                case (Wall.wallDown):
                case (Wall.laserWallDown):
                case (Wall.doubleLaserWallDown):
                    if (dx == 0 && dy == 1) return false;
                    break;

                case (Wall.wallUp):
                case (Wall.laserWallUp):
                case (Wall.doubleLaserWallUp):
                    if (dx == 0 && dy == -1) return false;
                    break;

                case (Wall.wallUpLeft):
                    if ((dx == 0 && dy == -1) || (dx == 1 && dy == 0)) return false;
                    break;

                case (Wall.wallUpRight):
                    if ((dx == 0 && dy == -1) || (dx == -1 && dy == 0)) return false;
                    break;

                case (Wall.wallDownLeft):
                    if ((dx == 0 && dy == 1) || (dx == 1 && dy == 0)) return false;
                    break;

                case (Wall.wallDownRight):
                    if ((dx == 0 && dy == 1) || (dx == -1 && dy == 0)) return false;
                    break;

                default:
                    throw new IllegalStateException("Unexpected wallID: " + wallID);
            }
        }
        return true;
    }


    public boolean checkOutOfWall(int x, int y, int dx, int dy) {
        if (gameMap.isThereWallOnThisPosition(x, y)) {
            int wallID = gameMap.getAssetLayerID(x, y);
            switch (wallID) {
                case (Wall.wallLeft):
                case (Wall.laserWallLeft):
                case (Wall.doubleLaserWallLeft):
                    if (dx == -1 && dy == 0) return false;
                    break;

                case (Wall.wallRight):
                case (Wall.laserWallRight):
                case (Wall.doubleLaserWallRight):
                    if (dx == 1 && dy == 0) return false;
                    break;

                case (Wall.wallDown):
                case (Wall.laserWallDown):
                case (Wall.doubleLaserWallDown):
                    if (dx == 0 && dy == -1) return false;
                    break;

                case (Wall.wallUp):
                case (Wall.laserWallUp):
                case (Wall.doubleLaserWallUp):
                    if (dx == 0 && dy == 1) return false;
                    break;

                case (Wall.wallDownRight):
                    if ((dx == 0 && dy == -1) || (dx == 1 && dy == 0)) return false;
                    break;

                case (Wall.wallDownLeft):
                    if ((dx == 0 && dy == -1) || (dx == -1 && dy == 0)) return false;
                    break;

                case (Wall.wallUpRight):
                    if ((dx == 0 && dy == 1) || (dx == 1 && dy == 0)) return false;
                    break;

                case (Wall.wallUpLeft):
                    if ((dx == 0 && dy == 1) || (dx == -1 && dy == 0)) return false;
                    break;


                default:
                    throw new IllegalStateException("Unexpected value: " + wallID);
            }
        }
        return true;
    }

}
