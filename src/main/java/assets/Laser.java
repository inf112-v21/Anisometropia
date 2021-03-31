package assets;

import actor.Player;
import map.GameMap;

public class Laser extends DamageAssets {

    @Override
    public void updatePlayersHealth(Player player, GameMap gameMap) {
        if (gameMap.isThereLaserBeamsOnThisPosition(player.getX(), player.getY())) {
            int laserID = gameMap.getLaserLayerID(player.getX(), player.getY());
            getLaserBeam(laserID, player);
        }
    }

    public void getLaserBeam(int laserID, Player player){
        switch (laserID) {
            case (laserBeamHorizontal):
            case (laserBeamVertical):
                player.updateDamageTokens(1);
                break;

            //Player is to be damaged and lose 2 health points.
            case (doubleLaserBeamHorizontal):
            case (doubleLaserBeamVertical):
            case (laserBeamCrossing):
                player.updateDamageTokens(2);
                break;

            case (doubleLaserBeamCrossing):
                player.updateDamageTokens(4);
                break;

            default:
                throw new IllegalStateException("Unexpected LaserID: " +laserID+ " x: " +player.getX()+ " y: " +player.getY());
        }
    }
}
