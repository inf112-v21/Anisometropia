package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class Player {
    int x, y, spawnX, spawnY;
    TextureRegion[][] playerImages;
    TiledMapTileLayer.Cell playerCell, playerWonCell, playerDiedCell;
    TiledMapTileLayer.Cell currentCell;
    GameMap gameMap;

    public Player(int x, int y, GameMap gameMap) {
        this.x = this.spawnX = x;
        this.y = this.spawnY = y;
        this.gameMap = gameMap;
        playerImages  = TextureRegion.split(new Texture("player.png"), 300, 300);
        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][0]));
        currentCell = playerCell;
        playerWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][2]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerImages[0][1]));
    }

    public boolean canMove(int dx, int dy) {
        return (x+dx >= 0 && x+dx < gameMap.getWidth()) && (y+dy >= 0 && y+dy < gameMap.getHeight());
    }

    public void move(int dx, int dy) {
        if (canMove(dx, dy)) {
            gameMap.setCell(x, y, "PlayerLayer", null);
            gameMap.setCell(x += dx, y += dy,"PlayerLayer", currentCell);
        }
    }

    public void respawn() {
        gameMap.setCell(x, y, "PlayerLayer", null);
        gameMap.setCell(spawnX, spawnY,"PlayerLayer", currentCell);
        this.x=spawnX;
        this.y=spawnY;
    }

    public TiledMapTileLayer.Cell getCell() {
        return currentCell;
    }

    public void playerAlive() {
        gameMap.setCell(x, y, "PlayerLayer", playerCell);
        currentCell = playerCell;
    }

    public void playerWins() {
        gameMap.setCell(x, y, "PlayerLayer", playerWonCell);
        currentCell = playerWonCell;
    }

    public void playerDies() {
        gameMap.setCell(x, y, "PlayerLayer", playerDiedCell);
        currentCell = playerDiedCell;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
