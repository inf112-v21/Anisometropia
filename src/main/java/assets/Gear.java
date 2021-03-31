package assets;

import actor.Player;
import map.GameMap;

public class Gear extends MovingAssets {
    @Override
    public void playerIsToMove(Player player, GameMap gameMap) {
        if (gameMap.isThereGearOnThisPosition(player.getX(), player.getY())) {
            int gearID = gameMap.getAssetLayerID(player.getX(), player.getY());
            gear(gearID, player);
        }
    }


    public void gear(int gearID, Player player){
        switch (gearID){
            case (gearRotatingLeft):
                player.rotate(3);
                break;

            case (gearRotatingRight):
                player.rotate(1);
                break;
        }
    }
}
