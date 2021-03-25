package assets;

import actor.Player;
import map.GameMap;

public class ConveyorBelts {
    public static final int yConveyorBeltID_Down      = 50, yConveyorBeltID_Right     = 52, yConveyorBeltID_Up      = 49, yConveyorBeltID_Left     = 51,
            yConveyorBeltID_DownRight = 40, yConveyorBeltID_RightUp   = 42, yConveyorBeltID_UpLeft  = 34, yConveyorBeltID_LeftDown = 33,
            yConveyorBeltID_DownLeft  = 44, yConveyorBeltID_RightDown = 36, yConveyorBeltID_UpRight = 35, yConveyorBeltID_LeftUp   = 43,
            bConveyorBeltID_Down      = 21, bConveyorBeltID_Right     = 14, bConveyorBeltID_Up      = 13, bConveyorBeltID_Left     = 22,
            bConveyorBeltID_DownRight = 25, bConveyorBeltID_RightUp   = 26, bConveyorBeltID_UpRight = 19, bConveyorBeltID_LeftUp   = 27,
            bConveyorBeltID_DownLeft  = 28, bConveyorBeltID_RightDown = 20, bConveyorBeltID_UpLeft  = 18, bConveyorBeltID_LeftDown = 17;

    //runConveyorBelt checks if a player stands on a conveyorBelt and if he does he returns the tileID
    //and uses the tileID to figure out in what direction the player is to move.


    /**
     * @param player Move the player in a given location
     * @param gameMap checks if a player stands on a conveyorBeltTile
     *
     * This function will move the player in given location:
     *      once if the ConveyorBelt is Yellow
     *      twice if the ConveyorBelt is Blue
     */
    public void isPlayerOnConveyorBelt(Player player, GameMap gameMap) {
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
                throw new IllegalStateException("Unexpected value for ConveyorClass: " + yConveyorID + " i posisjon x: " + player.getX() + " y: " + player.getY());
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
                throw new IllegalStateException("Unexpected value for ConveyorClass: " + bConveyorID + "i posisjon x: " + player.getX() + "y: " + player.getY());
        }
    }
}
