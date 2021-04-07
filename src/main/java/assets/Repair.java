package assets;

import actor.Player;
import map.GameMap;

public class Repair extends DamageAssets {
    @Override
    public void updatePlayersHealth(Player player, GameMap gameMap) {
        if (gameMap.isThereRepairStationOnThisPosition(player.getX(), player.getY())) {
            int wrenchID = gameMap.getAssetLayerID(player.getX(), player.getY());
            repairSite(wrenchID, player);
        }
    }

    //TODO when player walks on a doubleWrench Player is to draw an OPTION CARD
    public void repairSite(int wrenchID, Player player){
        switch (wrenchID){
            case (wrench):
                if(player.getDmgTokens()>0){
                    player.updateDamageTokens(-1);
                }
                break;

            case (doubleWrench):
                if(player.getDmgTokens()>0) {
                    player.updateDamageTokens(-1);
                }
        }
    }
}
