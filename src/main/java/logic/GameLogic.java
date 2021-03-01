package logic;

import actor.Player;
import cards.DeckOfRegisterCards;
import logic.PlayerQueue;
import map.GameMap;

public class GameLogic {
    GameMap gameMap;
    PlayerQueue playerQueue;

    final int FLAG_1_ID = 55, FLAG_2_ID = 63, FLAG_3_ID = 71, FLAG_4_ID = 79;
    final int yConveyorBeltID_Down      = 50, yConveyorBeltID_Right     = 52, yConveyorBeltID_Up      = 49, yConveyorBeltID_Left     = 51,
              yConveyorBeltID_DownRight = 58, yConveyorBeltID_RightUp   = 57, yConveyorBeltID_UpLeft  = 60, yConveyorBeltID_LeftDown = 59,
              yConveyorBeltID_DownLeft  = 68, yConveyorBeltID_RightDown = 67, yConveyorBeltID_UpRight = 66, yConveyorBeltID_LeftUp   = 65,
              bConveyorBeltID_Down      = 21, bConveyorBeltID_Right     = 14, bConveyorBeltID_Up      = 13, bConveyorBeltID_Left     = 22,
              bConveyorBeltID_DownRight = 25, bConveyorBeltID_RightUp   = 26, bConveyorBeltID_UpRight = 19, bConveyorBeltID_LeftUp   = 27,
              bConveyorBeltID_DownLeft  = 28, bConveyorBeltID_RightDown = 20, bConveyorBeltID_UpDown  = 18, bConveyorBeltID_LeftDown = 17;
    final int StartPosID_1 = 121, StartPosID_2 = 122, StartPosID_3 = 123, StartPosID_4 = 124, StartPosID_5 = 125, StartPosID_6 = 126, StartPosID_7 = 127, StartPosID_8 = 128;

    public static boolean gameOver = false;
    public static String gameMessage;

    boolean turnOver = true;

    public GameLogic(GameMap gameMap) {
        this.gameMap = gameMap;
        this.playerQueue = new PlayerQueue();

        addPlayer(2,2, "Player 1");
        addPlayer(7,2, "Player 2");
    }

    public void addPlayer(int spawnX, int spawnY, String name) {
        playerQueue.add(new Player(spawnX, spawnY, name, gameMap));
        gameMap.setPlayerPosition(spawnX, spawnY, playerQueue.getPlayerQueue().get(playerQueue.getPlayerQueue().size() - 1));
    }

    public void update() {
        if (turnOver) {
            turnOver = false;
            for (Player player : playerQueue.getPlayerQueue()) {
                if (gameMap.isThereFlagHere(player.getX(), player.getY())){
                    int tileID = gameMap.getAssetLayerID(player.getX(), player.getY());
                    registerFlag(tileID, player);
                }

                if (gameMap.isThereConveyorOnThisPosition(player.getX(), player.getY())){
                    int tileID = gameMap.getAssetLayerID(player.getX(), player.getY());
                    conveyorBelt(tileID, player);
                }

                if (checkWin(player)){
                    player.playerWins();
                    gameMap.setPlayerPosition(player.getX(), player.getY(), player);
                    gameMessage = player.playerName + " won the game!";
                    gameOver = true;
                }

                if (checkLoss(player.getX(), player.getY())) {
                    player.playerDies();
                    gameMap.setPlayerPosition(player.getX(), player.getY(), player);
                    gameMessage = player.playerName + " lost the game!";
                    gameOver = true;
                }
            }
            if (playerQueue.getCurrentPlayer() == playerQueue.getPlayerQueue().get(0)) {
                DeckOfRegisterCards deckOfRegisterCards = new DeckOfRegisterCards();
                for (Player player : playerQueue.getPlayerQueue()) {
                    player.setDealtRegisterCards(deckOfRegisterCards.dealNineCards());
                }
            }
        }
    }

    /**
     * Executes chosen cards of all players, one card at a time, chronologically.
     */
    public void executeChosenCards() {
        for (int i = 0; i < 5; i++) {
            for (Player player : playerQueue.getPlayerQueue()) {
                player.getChosenRegisterCards().get(i).executeRegister(player);
            }
        }

    }

    /*
    * The yellow ConveyorBelt will move the player 1 tile at the end of the round.
    * @return Moves and rotates the player on a conveyorBelt.
    * TODO: Add a method for Blue ConveyorBelts and moving the methods in the gameAssets map.
    * TODO: Make sure the game remember the previous move made on a yellow-corner-conveyorbelt such that if its stuck for another round it will move in one of the directions.
     */

    public void conveyorBelt (int tileID, Player player){
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

//            case (yConveyorBeltID_DownRight): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(3);}
//                //player.move(1,0);
//                break;
//            case (yConveyorBeltID_RightUp): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(3);}
//                //player.move(0,1);
//                break;
//            case (yConveyorBeltID_UpLeft): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(3);}
//                //player.move(-1,0);
//                break;
//            case (yConveyorBeltID_LeftDown): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(3);}
//                //player.move(0,-1);
//                break;
//
//
//            case (conveyorBeltID_DownLeft): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(1);}
//                //player.move(-1,0);
//                break;
//            case (conveyorBeltID_LeftUp): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(1);}
//                //player.move(0,1);
//                break;
//            case (conveyorBeltID_UpRight): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(1);}
//                //player.move(1,0);
//                break;
//            case (conveyorBeltID_RightDown): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(1);}
//                //player.move(0,-1);
//                break;

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

//            case (bConveyorBeltID_DownRight): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(3);}
//                player.move(1,0);
//                break;
//            case (bConveyorBeltID_RightUp): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(3);}
//                player.move(0,1);
//                break;
//            case (bConveyorBeltID_UpLeft): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(3);}
//                player.move(-1,0);
//                break;
//            case (bConveyorBeltID_LeftDown): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(3);}
//                player.move(0,-1);
//                break;
//
//
//            case (bConveyorBeltID_DownLeft): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(1);}
//                player.move(-1,0);
//                break;
//            case (bConveyorBeltID_LeftUp): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(1);}
//                player.move(0,1);
//                break;
//            case (bConveyorBeltID_UpRight): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(1);}
//                player.move(1,0);
//                break;
//            case (bConveyorBeltID_RightDown): if(player.conveyorBeltReached){
//                getCurrentPlayer().rotate(1);}
//                player.move(0,-1);
//                break;
        }
    }

    public void registerFlag (int tileID, Player player) {
        switch (tileID) {
            case (FLAG_1_ID): player.flagsReached[0] = true;
            break;
            case (FLAG_2_ID): if (player.flagsReached[0]) player.flagsReached[1] = true;
            break;
            case (FLAG_3_ID): if (player.flagsReached[1]) player.flagsReached[2] = true;
            break;
            case (FLAG_4_ID): if (player.flagsReached[2]) player.flagsReached[3] = true;
            break;
        }}

    public void gameReset() {
        gameOver = false;
        playerQueue.turnCounter = 0;
        for (Player player : playerQueue.getPlayerQueue()){
            player.respawn();
        }
    }

    public boolean checkWin(Player player) {
        return player.hasWon();
    }

    public boolean checkLoss(int x, int y) {
        return gameMap.isThereHoleOnThisPosition(x, y);
    }

    public Player getCurrentPlayer() {
        return playerQueue.getCurrentPlayer();
    }

    public Player getLastPlayer() {
        return playerQueue.getLastPlayer();
    }

    public PlayerQueue getPlayerQueue() {
        return playerQueue;
    }

    public void setTurnOverToTrue() {
        turnOver = true;
    }
}