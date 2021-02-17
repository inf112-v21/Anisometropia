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
        if (checkWin(player.getX(), player.getY())) {
            player.playerWins();
            gameMessage = "Game Won!";
            gameOver = true;
        }
        if(checkLoss(player.getX(), player.getY())) {
            player.playerDies();
            gameMessage = "Game Lost!";
            gameOver = true;
        }
    }

    public void gameReset() {
        gameOver = false;
        player.playerAlive();
        player.respawn();
    }

    public boolean checkWin(int x, int y) {
        return ((TiledMapTileLayer) tiledMap.getLayers().get("FlagLayer")).getCell(x, y) != null;
    }

    public boolean checkLoss(int x, int y) {
        return ((TiledMapTileLayer) tiledMap.getLayers().get("HoleLayer")).getCell(x, y) != null;
    }

    public Player getPlayer() {
        return player;
    }
}
