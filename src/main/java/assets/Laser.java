package assets;

import actor.Player;
import map.GameMap;

public class Laser {
    final int  laserBeamHorizontal = 38, laserBeamVertical = 46, laserBeamCrossing =39,
                doubleLaserBeamHorizontal = 101, doubleLaserBeamVertical = 102, doubleLaserBeamCrossing = 100 ,
                laserShootingNorth = 36, laserShootingSouth= 44, laserShootingWest = 44, LaserShootingEast = 37,
                doubleLaserShootingNorth  = 86, doubleLaserShootingSouth = 93, doubleLaserShootingWest= 94, doubleLaserShootingEast=92;

    public void isPlayerHitByLaserBeam(Player player, GameMap gameMap){
        if (gameMap.isThereLaserBeamsOnThisPosition(player.getX(), player.getY())) {
            int tileID = gameMap.getAssetLayerID(player.getX(), player.getY());
            getLaserBeam(tileID, player);
        }
    }



    public void getLaserBeam(int tileID, Player player){
        switch (tileID) {
            case (laserBeamHorizontal):
                if (player.laserBeamReached) {
                    player.move(0,0);
                }
                break;

            case (laserBeamVertical):
                if (player.laserBeamReached) {
                    player.move(0,0);
                }
                break;

            case (laserBeamCrossing):
                if (player.laserBeamReached){
                    player.move(0,0);
                }
                break;

            case (doubleLaserBeamHorizontal):
                if (player.laserBeamReached){
                    player.move(0,0);
                }
                break;
            case (doubleLaserBeamVertical):

                if (player.laserBeamReached){
                    player.move(0,0);
                }
                break;

            case (doubleLaserBeamCrossing):
                if (player.laserBeamReached){
                    player.move(0,0);
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + tileID);
        }
    }
}
