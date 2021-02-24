package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class GameLogic {
    GameScreen gameScreen;
    TiledMap tiledMap;

    PlayerQueue playerQueue;
    Player player1;
    Player player2;

    final int FLAG_1_ID = 55, FLAG_2_ID = 63, FLAG_3_ID = 71, FLAG_4_ID = 79;
    final int conveyorBeltID_Down      = 50, conveyorBeltID_Right     = 52, conveyorBeltID_Up      = 49, conveyorBeltID_Left     = 51,
              conveyorBeltID_DownRight = 58, conveyorBeltID_RightUp   = 57, conveyorBeltID_UpLeft  = 60, conveyorBeltID_LeftDown = 59,
              conveyorBeltID_DownLeft  = 68, conveyorBeltID_RightDown = 67, conveyorBeltID_UpRight = 66, conveyorBeltID_LeftUp   = 65;

    public static boolean gameOver = false;
    public static String gameMessage;

    public GameLogic(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.tiledMap = gameScreen.getGameMap().getTiledMap();
        player1 = new Player(2, 2, "Player 1", gameScreen.getGameMap());
        player2 = new Player(7, 2, "Player 2", gameScreen.getGameMap());
        playerQueue = new PlayerQueue();
        playerQueue.add(player1);
        gameScreen.getGameMap().setCell(player1.getX(), player1.getY(), "PlayerLayer", player1.getCell());

//        // code below can be removed to only have one player
        playerQueue.add(player2);
        gameScreen.getGameMap().setCell(player2.getX(), player2.getY(), "PlayerLayer", player2.getCell());
//        // end
    }

    public void update() {

        for (Player player : playerQueue.getPlayerQueue()) {
            if (((TiledMapTileLayer) tiledMap.getLayers().get("GameAssets")).getCell(player.x, player.y) != null){
                int tileID = ((TiledMapTileLayer) tiledMap.getLayers().get("GameAssets")).getCell(player.x, player.y).getTile().getId();
                registerFlag(tileID, player);
                conveyorBelt(tileID, player);
            }

            if(checkWin(player)){
                player.playerWins();
                gameMessage = player.playerName + " won the game!";
                gameOver = true;
            }

            if(checkLoss(player.getX(), player.getY())) {
                player.playerDies();
                gameMessage = player.playerName + " lost the game!";
                gameOver = true;
            }
        }


    }

    /*
    * The yellow ConveyorBelt will move the player 1 tile at the end of the round.
    * @return Flytter og Roterer spilleren når den går på et conveyorBelt.
    * TODO: Lage en metode slik at spilleren beveger seg kun en tile, selv om den neste tilen den lander på er et conveyorBelt.
     */

    public void conveyorBelt (int tileID, Player player){
        switch (tileID){

            case (conveyorBeltID_Down): if (player.conveyorBeltReached) {
                player.move(0,-1);}
                break;
            case (conveyorBeltID_Up): if(player.conveyorBeltReached) {
                player.move(0,1);}
                break;
            case (conveyorBeltID_Right): if(player.conveyorBeltReached){
                player.move(1, 0);
                playerQueue.next();}
                break;
            case (conveyorBeltID_Left): if(player.conveyorBeltReached){
                player.move(-1, 0);}
                break;

            case (conveyorBeltID_DownRight): if(player.conveyorBeltReached){
                getCurrentPlayer().rotate(-1);}
                //player.move(1,0);
                break;
            case (conveyorBeltID_RightUp): if(player.conveyorBeltReached){
                getCurrentPlayer().rotate(-1);}
                //player.move(0,1);
                break;
            case (conveyorBeltID_UpLeft): if(player.conveyorBeltReached){
                getCurrentPlayer().rotate(-1);}
                //player.move(-1,0);
                break;
            case (conveyorBeltID_LeftDown): if(player.conveyorBeltReached){
                getCurrentPlayer().rotate(-1);}
                //player.move(0,-1);
                break;


            case (conveyorBeltID_DownLeft): if(player.conveyorBeltReached){
                getCurrentPlayer().rotate(1);}
                //player.move(-1,0);
                break;
            case (conveyorBeltID_LeftUp): if(player.conveyorBeltReached){
                getCurrentPlayer().rotate(1);}
                //player.move(0,1);
                break;
            case (conveyorBeltID_UpRight): if(player.conveyorBeltReached){
                getCurrentPlayer().rotate(1);}
                //player.move(1,0);
                break;
            case (conveyorBeltID_RightDown): if(player.conveyorBeltReached){
                getCurrentPlayer().rotate(1);}
                //player.move(0,-1);
                break;
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
        return ((TiledMapTileLayer) tiledMap.getLayers().get("HoleLayer")).getCell(x, y) != null;
    }



/*    public boolean checkWin(int x, int y) {
        return ((TiledMapTileLayer) tiledMap.getLayers().get("FlagLayer")).getCell(x, y) != null;
    }

    public boolean checkLoss(int x, int y) {
        return ((TiledMapTileLayer) tiledMap.getLayers().get("HoleLayer")).getCell(x, y) != null;
    }*/

    public Player getCurrentPlayer() {
        return playerQueue.getCurrentPlayer();
    }
}