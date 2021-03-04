package assets;


import actor.IPlayer;
import actor.Player;
import map.GraphicalGameMap;

public class ConveyorBelts {

    GraphicalGameMap gameMap;
    Player player;
    IPlayer currentPlayer;

    final int yConveyorBeltID_Down      = 50, yConveyorBeltID_Right     = 52, yConveyorBeltID_Up      = 49, yConveyorBeltID_Left     = 51,
              yConveyorBeltID_DownRight = 58, yConveyorBeltID_RightUp   = 57, yConveyorBeltID_UpLeft  = 60, yConveyorBeltID_LeftDown = 59,
              yConveyorBeltID_DownLeft  = 68, yConveyorBeltID_RightDown = 67, yConveyorBeltID_UpRight = 66, yConveyorBeltID_LeftUp   = 65,
              bConveyorBeltID_Down      = 21, bConveyorBeltID_Right     = 14, bConveyorBeltID_Up      = 13, bConveyorBeltID_Left     = 22,
              bConveyorBeltID_DownRight = 25, bConveyorBeltID_RightUp   = 26, bConveyorBeltID_UpRight = 19, bConveyorBeltID_LeftUp   = 27,
              bConveyorBeltID_DownLeft  = 28, bConveyorBeltID_RightDown = 20, bConveyorBeltID_UpLeft  = 18, bConveyorBeltID_LeftDown = 17;

    public void runConveyorBelt() {
        if (gameMap.isThereConveyorOnThisPosition(player.getX(), player.getY())){
            int tileID = gameMap.getAssetLayerID(player.getX(), player.getY());
            conveyorBelt(tileID, player);
        }
    }

    /*
     * The yellow ConveyorBelt will move the player 1 tile at the end of the round.
     * @return Moves and rotates the player on a conveyorBelt.
     * TODO: Add a method for Blue ConveyorBelts and moving the methods in the gameAssets map.
     * TODO: Make sure the game remember the previous move made on a yellow-corner-conveyorbelt such that if its stuck for another round it will move in one of the directions.
     */
    private void conveyorBelt(int tileID, Player player){
        switch (tileID){
            case (yConveyorBeltID_Down): if (player.conveyorBeltReached) {
                player.move(0,-1);}
                break;
            case (yConveyorBeltID_Up): if(player.conveyorBeltReached) {
                player.move(0,1);}
                break;
            case (yConveyorBeltID_Right): if(player.conveyorBeltReached){
                player.move(1, 0); }
                break;
            case (yConveyorBeltID_Left): if(player.conveyorBeltReached){
                player.move(-1, 0);}
                break;

//need to look at the previous move/round done by player. if old pos == new pos the player is to move in a direction and not rotate.
//this only needs to be added to the yellow conveyorBelts, as the blue conveyorBelts does this in one operation and won't stand
//in the same position for several rounds.
//This has a very low priority, as it rarely occurs.

            case (yConveyorBeltID_DownRight): if(player.conveyorBeltReached){
                currentPlayer.rotate(3);}
                //player.move(1,0);
                break;
            case (yConveyorBeltID_RightUp): if(player.conveyorBeltReached){
                currentPlayer.rotate(3);}
                //player.move(0,1);
                break;
            case (yConveyorBeltID_UpLeft): if(player.conveyorBeltReached){
                currentPlayer.rotate(3);}
                //player.move(-1,0);
                break;
            case (yConveyorBeltID_LeftDown): if(player.conveyorBeltReached){
                currentPlayer.rotate(3);}
                //player.move(0,-1);
                break;


            case (yConveyorBeltID_DownLeft): if(player.conveyorBeltReached){
                currentPlayer.rotate(1);}
                //player.move(-1,0);
                break;
            case (yConveyorBeltID_LeftUp): if(player.conveyorBeltReached){
                currentPlayer.rotate(1);}
                //player.move(0,1);
                break;
            case (yConveyorBeltID_UpRight): if(player.conveyorBeltReached){
                currentPlayer.rotate(1);}
                //player.move(1,0);
                break;
            case (yConveyorBeltID_RightDown): if(player.conveyorBeltReached){
                currentPlayer.rotate(1);}
                //player.move(0,-1);
                break;

            case (bConveyorBeltID_Down): if (player.conveyorBeltReached) {
                player.move(0,-2);}
                break;
            case (bConveyorBeltID_Up): if(player.conveyorBeltReached) {
                player.move(0,2);}
                break;
            case (bConveyorBeltID_Right): if(player.conveyorBeltReached){
                player.move(2, 0);}
                break;
            case (bConveyorBeltID_Left): if(player.conveyorBeltReached){
                player.move(-2, 0);}
                break;

            case (bConveyorBeltID_DownRight): if(player.conveyorBeltReached){
                currentPlayer.rotate(3);}
                player.move(1,0);
                break;
            case (bConveyorBeltID_RightUp): if(player.conveyorBeltReached){
                currentPlayer.rotate(3);}
                player.move(0,1);
                break;
            case (bConveyorBeltID_UpLeft): if(player.conveyorBeltReached){
                currentPlayer.rotate(3);}
                player.move(-1,0);
                break;
            case (bConveyorBeltID_LeftDown): if(player.conveyorBeltReached){
                currentPlayer.rotate(3);}
                player.move(0,-1);
                break;


            case (bConveyorBeltID_DownLeft): if(player.conveyorBeltReached){
                currentPlayer.rotate(1);}
                player.move(-1,0);
                break;
            case (bConveyorBeltID_LeftUp): if(player.conveyorBeltReached){
                currentPlayer.rotate(1);}
                player.move(0,1);
                break;
            case (bConveyorBeltID_UpRight): if(player.conveyorBeltReached){
                currentPlayer.rotate(1);}
                player.move(1,0);
                break;
            case (bConveyorBeltID_RightDown): if(player.conveyorBeltReached){
                currentPlayer.rotate(1);}
                player.move(0,-1);
                break;
        }
    }
}
