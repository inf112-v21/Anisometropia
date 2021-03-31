package assets;

import actor.Player;
import map.GameMap;

public class ConveyorBelts extends MovingAssets {
    @Override
    public void playerIsToMove(Player player, GameMap gameMap) {
        if (gameMap.isThereYellowConveyorOnThisPosition(player.getX(), player.getY())) {
            int yConveyorID = gameMap.getAssetLayerID(player.getX(), player.getY());
            yConveyorBelt(yConveyorID, player);
        }
        for (int x = 0; x<=1; x++){
            if (gameMap.isThereBlueConveyorOnThisPosition(player.getX(),player.getY())) {
                int bConveyorID = gameMap.getAssetLayerID(player.getX(), player.getY());
                bConveyorBelt(bConveyorID, player);
            }
        }
    }

    /**
     * @param yConveyorID - ID to a given tile
     * @param player - Moves the player
     *
     *Yellow ConveyorBelt moves player at the end of each round
     */
    private void yConveyorBelt(int yConveyorID, Player player) {
        switch (yConveyorID) {
//--------------- Yellow ConveyorBelts --------------
            case (yConveyorBeltID_Down):
                player.move(0, -1);
                break;
            case (yConveyorBeltID_Up):
                player.move(0, 1);
                break;
            case (yConveyorBeltID_Right):
                player.move(1, 0);
                break;
            case (yConveyorBeltID_Left):
                player.move(-1, 0);
                break;

            case (yConveyorBeltID_DownRight):
                player.rotate(3);
                player.move(1, 0);
                break;
            case (yConveyorBeltID_RightUp):
                player.rotate(3);
                player.move(0, 1);
                break;
            case (yConveyorBeltID_UpLeft):
                player.rotate(3);
                player.move(-1, 0);
                break;
            case (yConveyorBeltID_LeftDown):
                player.rotate(3);
                player.move(0, -1);
                break;

            case (yConveyorBeltID_DownLeft):
                player.rotate(1);
                player.move(-1, 0);
                break;
            case (yConveyorBeltID_LeftUp):
                player.rotate(1);
                player.move(0, 1);
                break;
            case (yConveyorBeltID_UpRight):
                player.rotate(1);
                player.move(1, 0);
                break;
            case (yConveyorBeltID_RightDown):
                player.rotate(1);
                player.move(0, -1);
                break;

            default:
                throw new IllegalStateException("Unexpected yConveyorID: " + yConveyorID + " x: " + player.getX() + " y: " + player.getY());
        }
    }

    //-------------- Blue ConveyorBelts ---------------
    /**
     * @param bConveyorID ID to a given tile
     * @param player Moves player in a given location
     * Blue ConveyorBelt moves player at the end of each round
     */
    public void bConveyorBelt(int bConveyorID, Player player){
        switch (bConveyorID){
            case (bConveyorBeltID_Down):
                player.move(0, -1);
                break;
            case (bConveyorBeltID_Up):
                player.move(0, 1);
                break;
            case (bConveyorBeltID_Right):
                player.move(1, 0);
                break;
            case (bConveyorBeltID_Left):
                player.move(-1, 0);
                break;

            case (bConveyorBeltID_DownRight):
                player.rotate(3);
                player.move(1, 0);
                break;
            case (bConveyorBeltID_RightUp):
                player.rotate(3);
                player.move(0, 1);
                break;
            case (bConveyorBeltID_UpLeft):
                player.rotate(3);
                player.move(-1, 0);
                break;
            case (bConveyorBeltID_LeftDown):
                player.rotate(3);
                player.move(0, -1);
                break;
            case (bConveyorBeltID_DownLeft):
                player.rotate(1);
                player.move(-1, 0);
                break;
            case (bConveyorBeltID_LeftUp):
                player.rotate(1);
                player.move(0, 1);
                break;
            case (bConveyorBeltID_UpRight):
                player.rotate(1);
                player.move(1, 0);
                break;
            case (bConveyorBeltID_RightDown):
                player.rotate(1);
                player.move(0, -1);
                break;
            default:
                throw new IllegalStateException("Unexpected bConveyorID: " + bConveyorID + " x: " + player.getX() + " y: " + player.getY());
        }
    }


}
