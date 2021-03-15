package assets;

import actor.Player;
import map.GameMap;

public class Wall {

//The lasercannon is attached to a wall and is therfor defined as a wall instead of a laser. The laser it self will
//deal damage while a wall is to only stop a players movement.

    public final int   wallDownRight = 8, wallUpRight = 16, wallUpLeft = 26, wallDownLeft = 32,
                wallRight = 23, wallDown = 29, wallLeft = 30, wallUp = 31,
                laserWallDown = 37, laserWallUp= 45, laserWallRight = 46, laserWallLeft = 38,
                doubleLaserWallDown = 87, doubleLaserWallUp = 94, doubleLaserWallRight= 95, doubleLaserWallLeft=93;

    public void isPlayerOnWall(Player player, GameMap gameMap) {
        if (gameMap.isThereWallOnThisPosition(player.getX(), player.getY())) {
            int tileID = gameMap.getAssetLayerID(player.getX(), player.getY());
            wall(tileID, player);
            System.out.println("player is on a wall");
        }
    }

    public void wall (int tileID, Player player){
        switch (tileID){
            case (wallDownRight):
                if (player.wallHasBeenReached){
                }
                break;

            case (wallUpRight):
                if (player.wallHasBeenReached){
                }
                break;

            case(wallDownLeft):
                if(player.wallHasBeenReached){
                }
                break;

            case(wallUpLeft):
                if (player.wallHasBeenReached){
                }
                break;

            case(wallRight):
            case(laserWallRight):
            case(doubleLaserWallRight):
                if (player.wallHasBeenReached){
                }
                break;

            case(wallUp):
            case(laserWallUp):
            case(doubleLaserWallUp):
                if (player.wallHasBeenReached){
                }
                break;

            case(wallDown):
            case(laserWallDown):
            case(doubleLaserWallDown):
                if (player.wallHasBeenReached){
                }
                break;

            case(wallLeft):
            case(laserWallLeft):
            case(doubleLaserWallLeft):
                if (player.wallHasBeenReached){
                }
                break;

        }
    }

}
