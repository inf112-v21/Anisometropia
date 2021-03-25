package assets;

import actor.Player;
import map.GameMap;

public class Wall {

//The lasercannon is attached to a wall and is therefore defined as a wall instead of a laser. The laser it self will
//deal damage while a wall is to only stop a players movement.

    /**
     * Brukerhistorier:
     * Som spiller vil jeg at spillere ikke skal kunne gå gjennom vegger, slik at spillebrettet blir mer variert.
     * <p>
     * Akseptansekriterier:
     * Når en robot står inntil en vegg og beveger seg i veggens retning skal den ikke forflytte seg.
     * <p>
     * Arbeidsoppgaver:
     * - Identifisere hvor veggene er på brettet og lagre disse som koordinater.
     * - Lage en metode som sjekker om en spiller er ved en vegg.
     * - En spiller skal ikke kunne bevege seg over en vegg.
     * <p>
     * <p>
     * - Skal spiller sjekke om den er nær en vegg, eller skal veggen sjekke om det er
     * en spiller i retningen North, South, West og East?
     */

    public static final int wallDownRight = 8;
    public static final int wallUpRight = 16;
    public static final int wallUpLeft = 26;
    public static final int wallDownLeft = 32;
    public static final int wallRight = 23;
    public static final int wallDown = 29;
    public static final int wallLeft = 30;
    public static final int wallUp = 31;
    public static final int laserWallDown = 37;
    public static final int laserWallUp = 45;
    public static final int laserWallRight = 46;
    public static final int laserWallLeft = 38;
    public static final int doubleLaserWallDown = 87;
    public static final int doubleLaserWallUp = 94;
    public static final int doubleLaserWallRight = 95;
    public static final int doubleLaserWallLeft = 93;

    public GameMap gameMap;

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
