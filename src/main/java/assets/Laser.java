package assets;

import actor.Player;
import map.GameMap;

public class Laser {
    public static final int
            laserBeamHorizontal = 39, laserBeamVertical = 47, laserBeamCrossing =40,
            doubleLaserBeamHorizontal = 102, doubleLaserBeamVertical = 103, doubleLaserBeamCrossing = 101;
    /**
     * @param player Damages the player\
     * @param gameMap checks if a player stands om a tile that is to damage the player\
     *                If the player stands on a tile that is to damage the player, the player should be updated\
     *                with the damageTokens corresponding to damage taken\
     */
    public void damagePlayer(Player player, GameMap gameMap) {
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
