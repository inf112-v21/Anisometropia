package assets;

import actor.Player;
import map.GameMap;

public class Laser {
    public static final int  laserBeamHorizontal = 39, laserBeamVertical = 47, laserBeamCrossing =40,
                             doubleLaserBeamHorizontal = 102, doubleLaserBeamVertical = 103, doubleLaserBeamCrossing = 101;

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
                    player.updateDamageTokens(1);
                }
                break;

//Player is to be damaged and loose 2 health points.
            case (doubleLaserBeamHorizontal):
            case (doubleLaserBeamVertical):
            case (laserBeamCrossing):
                if(player.laserBeamReached){
                    player.updateDamageTokens(2);
                }
                break;

            case (doubleLaserBeamCrossing):
                if(player.laserBeamReached){
                    player.updateDamageTokens(4);
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value for Laser Class: " + tileID);
        }
    }
}
