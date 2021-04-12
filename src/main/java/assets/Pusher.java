package assets;

import actor.Player;
import map.GameMap;

public class Pusher extends MovingAssets{
    @Override
    public void playerIsToMove(Player player, GameMap gameMap) {
        if (gameMap.isTherePusherOnThisPosition(player.getX(), player.getY())) {
            int pusherID = gameMap.getAssetLayerID(player.getX(), player.getY());
            pushPlayer(pusherID, player);
        }
    }

    public void pushPlayer (int pusherID, Player player){
        switch (pusherID){
            case (pusherDown):
                player.move(0, -1);
                break;
            case (pusherUp):
                player.move(0, 1);
                break;
            case (pusherLeft):
                player.move(-1, 0);
                break;
            case (pusherRight):
                player.move(1,0);
                break;
        }
    }
}
