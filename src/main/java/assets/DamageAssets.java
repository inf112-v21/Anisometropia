package assets;

import actor.Player;
import map.GameMap;

public abstract class DamageAssets {
    public static final int
            laserBeamHorizontal = 39, laserBeamVertical = 47, laserBeamCrossing =40,
            doubleLaserBeamHorizontal = 102, doubleLaserBeamVertical = 103, doubleLaserBeamCrossing = 101;

    public static final int wrench =15,  doubleWrench = 7;

    /**
     * @param player updates damage on player
     * @param gameMap checks if a player stands om a tile that is to damage/repair the player
     *  If the player stands on a tile that is to damage/repair the player, the player should be updated
     *  with the damageTokens corresponding to damage taken/repaired
     *  Negative - gains health, Positive - looses health
     */
    public abstract void updatePlayersHealth(Player player, GameMap gameMap);
}
