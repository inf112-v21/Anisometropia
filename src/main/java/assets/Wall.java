package assets;

import actor.Player;
import map.GameMap;

public class Wall {

//The lasercannon is attached to a wall and is therefore defined as a wall instead of a laser. The laser it self will
//deal damage while a wall is to only stop a players movement.

    /**
     * Brukerhistorier:
     * Som spiller vil jeg at spillere ikke skal kunne gå gjennom vegger, slik at spillebrettet blir mer variert.
     *
     * Akseptansekriterier:
     * Når en robot står inntil en vegg og beveger seg i veggens retning skal den ikke forflytte seg.
     *
     * Arbeidsoppgaver:
     * - Identifisere hvor veggene er på brettet og lagre disse som koordinater.
     * - Lage en metode som sjekker om en spiller er ved en vegg.
     * - En spiller skal ikke kunne bevege seg over en vegg.
     *
     *
     * - Skal spiller sjekke om den er nær en vegg, eller skal veggen sjekke om det er
     *   en spiller i retningen North, South, West og East?
     */

    final int   wallDownRight       =  8,  wallUpRight       = 16, wallUpLeft          = 26, wallDownLeft       = 32,
                wallRight           = 23,  wallDown          = 29, wallLeft            = 30, wallUp             = 31,
                laserWallDown       = 37,  laserWallUp       = 45, laserWallRight      = 46, laserWallLeft      = 38,
                doubleLaserWallDown = 87,  doubleLaserWallUp = 94, doubleLaserWallRight= 95, doubleLaserWallLeft= 93;

    public void isPlayerOnWall(Player player, GameMap gameMap) {
        if (gameMap.isThereWallOnThisPosition(player.getX(), player.getY())) {
            int tileID = gameMap.getAssetLayerID(player.getX(), player.getY());
       //     canPlayerWalkPastWall(tileID, player);
            System.out.println("player is on a wall");
        }
    }

    public boolean canPlayerWalkPastWall (int tileID, int dx, int dy, boolean standingOnTile, Player player ){ // Player player
        switch (tileID) {
            case (wallDownRight):
                if (player.wallHasBeenReached){
                    player.canMove(1,0);
                    player.canMove(0,-1);
                }
                break;

            case (wallUpRight):
                if (standingOnTile) {
                    if (dx == 1 && dy == 0) return false;
                    else if (dx == -1 && dy == 0) return true;
                    else if (dx == 0 && dy == 1) return false;
                    else if (dx == 0 && dy == -1) return true;
                }else{



                    }
                //check if moveForward collides with a wall. if True check where the wall is.
                //if playing is moving right into a wall tile and the wall is on the right side in that tile
                //the move is legal.


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
        return true;
    }

}
