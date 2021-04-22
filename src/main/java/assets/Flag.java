package assets;

import actor.Player;
import map.GameMap;

public class Flag {
    public static final int FLAG_1_ID = 55, FLAG_2_ID = 63, FLAG_3_ID = 71, FLAG_4_ID = 79;

    /**
     * If there is a flag on players position, playerReachedNewFlag is called.
     * Checks if there is a flag on Players position.
     * A player needs to visit the flag in order 1 - 4 and cant skip flags
     * When player has visited a flag, a new spawn location is given in case of death.
     */
    public void updateFlag(Player player, GameMap gameMap){
        if (gameMap.isThereFlagHere(player.getX(), player.getY())) {
            int tileID = gameMap.getAssetLayerID(player.getX(), player.getY());
            playerReachedNewFlag(tileID, player);
        }
    }

    //Player cant set a checkpoint on flag_(n+1) if flag_(n) hasn't been visited.
    public void playerReachedNewFlag (int tileID, Player player) {
        switch (tileID) {
            case (FLAG_1_ID):
                player.flagsReached[0] = true;
                player.setNewCheckpoint();
                break;
            case (FLAG_2_ID):
                if (player.flagsReached[0]){
                    player.flagsReached[1] = true;
                    player.setNewCheckpoint();
                }
                break;
            case (FLAG_3_ID):
                if (player.flagsReached[1]){
                    player.flagsReached[2] = true;
                    player.setNewCheckpoint();
                }
                break;
            case (FLAG_4_ID):
                if (player.flagsReached[2]){
                    player.flagsReached[3] = true;
                    player.setNewCheckpoint();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected LaserID: " +tileID+ " x: " +player.getX()+ " y: " +player.getY());
        }
    }

    
}
