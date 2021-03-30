package assets;

import actor.Player;
import map.GameMap;

public abstract class MovingAssets {
    public static final int //YellowConveyorBelts
            yConveyorBeltID_Down      = 50, yConveyorBeltID_Right     = 52, yConveyorBeltID_Up      = 49, yConveyorBeltID_Left     = 51,
            yConveyorBeltID_DownRight = 40, yConveyorBeltID_RightUp   = 42, yConveyorBeltID_UpLeft  = 34, yConveyorBeltID_LeftDown = 33,
            yConveyorBeltID_DownLeft  = 44, yConveyorBeltID_RightDown = 36, yConveyorBeltID_UpRight = 35, yConveyorBeltID_LeftUp   = 43;
    public static final int //BlueConveyorBelts
            bConveyorBeltID_Down      = 21, bConveyorBeltID_Right     = 14, bConveyorBeltID_Up      = 13, bConveyorBeltID_Left     = 22,
            bConveyorBeltID_DownRight = 25, bConveyorBeltID_RightUp   = 26, bConveyorBeltID_UpRight = 19, bConveyorBeltID_LeftUp   = 27,
            bConveyorBeltID_DownLeft  = 28, bConveyorBeltID_RightDown = 20, bConveyorBeltID_UpLeft  = 18, bConveyorBeltID_LeftDown = 17;
    public static final int //Gear
            gearRotatingLeft = 53, gearRotatingRight = 54;

    /**
     *@param player  Move the player in a given location
     *@param gameMap checks if a player stands on a tile that is to move the player
     *               this function will move and rotate player in given location.
     *               once if the ConveyorBelt is Yellow
     *               twice if it's blue
     *               rotate once if a gear
     */
    public abstract void movePlayer(Player player, GameMap gameMap);
}
