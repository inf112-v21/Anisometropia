package assets;

import actor.Player;
import map.GameMap;

public class ConveyorBelts {

    final int yConveyorBeltID_Down      = 50, yConveyorBeltID_Right     = 52, yConveyorBeltID_Up      = 49, yConveyorBeltID_Left     = 51,
              yConveyorBeltID_DownRight = 58, yConveyorBeltID_RightUp   = 57, yConveyorBeltID_UpLeft  = 60, yConveyorBeltID_LeftDown = 59,
              yConveyorBeltID_DownLeft  = 68, yConveyorBeltID_RightDown = 67, yConveyorBeltID_UpRight = 66, yConveyorBeltID_LeftUp   = 65,
              bConveyorBeltID_Down      = 21, bConveyorBeltID_Right     = 14, bConveyorBeltID_Up      = 13, bConveyorBeltID_Left     = 22,
              bConveyorBeltID_DownRight = 25, bConveyorBeltID_RightUp   = 26, bConveyorBeltID_UpRight = 19, bConveyorBeltID_LeftUp   = 27,
              bConveyorBeltID_DownLeft  = 28, bConveyorBeltID_RightDown = 20, bConveyorBeltID_UpLeft  = 18, bConveyorBeltID_LeftDown = 17;


    /**
     * runConveyorBelt checks if a player stands on a conveyorBelt and if he does he returns the tileID
     * and uses the tileID to figure out in what direction the player is to move.
     */
    public void runConveyorBelt(Player player, GameMap gameMap) {
            if (gameMap.isThereConveyorOnThisPosition(player.getX(), player.getY())) {
                int tileID = gameMap.getAssetLayerID(player.getX(), player.getY());
                conveyorBelt(tileID, player);
        }
    }

    /**
     * If the player has reached a conveyorBelt this method will find which case the tileID corresponds to
     * and then move it in the given direction.
     */
    private void conveyorBelt(int tileID, Player player) {
        switch (tileID) {
//--------------- Yellow ConveyorBelts --------------
            case (yConveyorBeltID_Down):
                if (player.conveyorBeltReached) {
                    player.move(0, -1);}
                break;
            case (yConveyorBeltID_Up):
                if (player.conveyorBeltReached) {
                    player.move(0, 1);}
                break;
            case (yConveyorBeltID_Right):
                if (player.conveyorBeltReached) {
                    player.move(1, 0);}
                break;
            case (yConveyorBeltID_Left):
                if (player.conveyorBeltReached) {
                    player.move(-1, 0);}
                break;

            case (yConveyorBeltID_DownRight):
                if (player.conveyorBeltReached) {
                    player.rotate(3);
                    player.move(1, 0);}
                break;
            case (yConveyorBeltID_RightUp):
                if (player.conveyorBeltReached) {
                    player.rotate(3);
                    player.move(0, 1);}
                break;
            case (yConveyorBeltID_UpLeft):
                if (player.conveyorBeltReached) {
                    player.rotate(3);
                    player.move(-1, 0);}
                break;
            case (yConveyorBeltID_LeftDown):
                if (player.conveyorBeltReached) {
                    player.rotate(3);
                    player.move(0, -1);}
                break;

            case (yConveyorBeltID_DownLeft):
                if (player.conveyorBeltReached) {
                    player.rotate(1);
                    player.move(-1, 0);}
                break;
            case (yConveyorBeltID_LeftUp):
                if (player.conveyorBeltReached) {
                    player.rotate(1);
                    player.move(0, 1);}
                break;
            case (yConveyorBeltID_UpRight):
                if (player.conveyorBeltReached) {
                    player.rotate(1);
                    player.move(1, 0);}
                break;
            case (yConveyorBeltID_RightDown):
                if (player.conveyorBeltReached) {
                    player.rotate(1);
                    player.move(0, -1);}
                break;

//-------------- Blue ConveyorBelts ---------------
// if the conveyorBelt ends so does the movement of that player.
// A player can't move further then one tile away from any conveyorBelt.

            case (bConveyorBeltID_Down):
                if (player.conveyorBeltReached) {
                    player.move(0, -2);}
                break;
            case (bConveyorBeltID_Up):
                if (player.conveyorBeltReached) {
                    player.move(0, 2);}
                break;

            case (bConveyorBeltID_Right):
                if (player.conveyorBeltReached) {
                    player.move(2, 0);}
            break;
            case (bConveyorBeltID_Left):
                if (player.conveyorBeltReached) {
                    player.move(-2, 0);}
                break;

            case (bConveyorBeltID_DownRight):
                if (player.conveyorBeltReached) {
                    player.rotate(3);
                    player.move(2, 0);}
                break;
            case (bConveyorBeltID_RightUp):
                if (player.conveyorBeltReached) {
                    player.rotate(3);
                    player.move(0, 2);}
                break;
            case (bConveyorBeltID_UpLeft):
                if (player.conveyorBeltReached) {
                    player.rotate(3);
                    player.move(-2, 0);}
                break;
            case (bConveyorBeltID_LeftDown):
                if (player.conveyorBeltReached) {
                    player.rotate(2);
                    player.move(0, -2);}
                break;
            case (bConveyorBeltID_DownLeft):
                if (player.conveyorBeltReached) {
                    player.rotate(1);
                    player.move(-2, 0);}
                break;
            case (bConveyorBeltID_LeftUp):
                if (player.conveyorBeltReached) {
                    player.rotate(1);
                    player.move(0, 2);}
                break;
            case (bConveyorBeltID_UpRight):
                if (player.conveyorBeltReached) {
                    player.rotate(1);
                    player.move(2, 0);}
                break;
            case (bConveyorBeltID_RightDown):
                if (player.conveyorBeltReached) {
                    player.rotate(1);
                    player.move(0, -2);}
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tileID);
        }
    }
}
