package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class GameLogic {
    GameScreen gameScreen;
    TiledMap tiledMap;
    Player player;

    public static boolean gameOver = false;
    public static String gameMessage;

    public GameLogic(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.player = new Player(1, 1, gameScreen.getGameMap());
        this.tiledMap = gameScreen.getGameMap().getTiledMap();
        gameScreen.getGameMap().setCell(1, 1, "PlayerLayer", player.getCell());
    }

    public void update() {


        if (((TiledMapTileLayer) tiledMap.getLayers().get("FlagLayer")).getCell(player.x, player.y) != null){
            System.out.println(((TiledMapTileLayer) tiledMap.getLayers().get("FlagLayer")).getCell(player.x, player.y).getTile().getId());
            int flagID = ((TiledMapTileLayer) tiledMap.getLayers().get("FlagLayer")).getCell(player.x, player.y).getTile().getId();
            registerFlag(flagID, player);
        }

        if(checkWin(player)){
            player.playerWins();
            gameMessage = "Game Won!";
            gameOver = true;
        }

        /*
        if (checkWin(player.getX(), player.getY())) {
            player.playerWins();
            gameMessage = "Game Won!";
            gameOver = true;
        }
        */
        if(checkLoss(player.getX(), player.getY())) {
            player.playerDies();
            gameMessage = "Game Lost!";
            gameOver = true;
        }
    }

    public void registerFlag (int flagID, Player player) {
        switch (flagID) {
            case (55): player.flagsReached[0] = true;
            break;
            case (63): if (player.flagsReached[0]) player.flagsReached[1] = true;
            break;
            case (71): if (player.flagsReached[1]) player.flagsReached[2] = true;
            break;
            case (79): if (player.flagsReached[2]) player.flagsReached[3] = true;
            break;
        }}

    public void gameReset() {
        gameOver = false;
        player.playerAlive();
        player.respawn();
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

    public Player getPlayer() {
        return player;
    }
}