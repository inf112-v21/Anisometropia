package assets;

import actor.Player;
import map.GameMap;

public class Laser {
    final int  laserBeamHorizontal = 38, laserBeamVertical = 46, laserBeamCrossing =39,
                doubleLaserBeamHorizontal = 101, doubleLaserBeamVertical = 102, doubleLaserBeamCrossing = 100;

    public void isPlayerHitByLaserBeam(Player player, GameMap gameMap){
        if (gameMap.isThereLaserBeamsOnThisPosition(player.getX(), player.getY())) {
            int tileID = gameMap.getLaserLayerID(player.getX(), player.getY());
            getLaserBeam(tileID, player);
        }
    }



    public void getLaserBeam(int tileID, Player player){
        switch (tileID) {
            case (laserBeamHorizontal):
            case (laserBeamVertical):
                if (player.laserBeamReached) {
                    player.playerGetsDamaged = true;
                    player.updateDamageTokens();
                }
                break;

//Player is to be damaged and loose 2 health points.
            case (doubleLaserBeamHorizontal):
            case (doubleLaserBeamVertical):
            case (laserBeamCrossing):
                if(player.laserBeamReached){
                    player.playerGetsDamaged = true;
                    player.updateDamageTokens();
                }
                break;

            case (doubleLaserBeamCrossing):
                if(player.laserBeamReached){
                    player.playerGetsDamaged = true;
                    player.updateDamageTokens();
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value for Laser Class: " + tileID);
        }
    }
}
