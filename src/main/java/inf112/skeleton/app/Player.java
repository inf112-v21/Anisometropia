package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class Player {
    int x, y, spawnX, spawnY;
    int dmgTokens = 0;
    boolean powerDown = false;
    int direction;
    TextureRegion[][] playerImages;
    TiledMapTileLayer.Cell playerCell, playerWonCell, playerDiedCell;
    TiledMapTileLayer.Cell currentCell;
    GameMap gameMap;

    public Player(int x, int y, GameMap gameMap) {
        this.x = this.spawnX = x;
        this.y = this.spawnY = y;
        this.direction = 0; // Initially faces NORTH
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

    /**
     * Moves player given amount of tiles in current facing direction.
     * @param amountToMove denotes amount of tiles to move
     */
    public void moveByDirection(int amountToMove) {
        switch (getDirection()) {
            case 0:
                move(0, amountToMove);
                break;
            case 1:
                move(amountToMove, 0);
                break;
            case 2:
                move(0, -amountToMove);
                break;
            case 3:
                move(-amountToMove, 0);
                break;
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

    public void playerDamaged() {
        this.dmgTokens += 1;
    }

    public void playerPowerDown() {
        /*
        Don't forget about the powerDown signal that needs to be given 1 turn in advance.
        Signaling method also needs to be implemented for deciding to remain or leave the
        powerDown state. We should probably implement this in GameLogic since it communicates
        with user input and can keep track of turns.
         */
        this.dmgTokens = 0;
        this.powerDown = true;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * 0 denotes NORTH, 1 denotes EAST, 2 denotes SOUTH, 3 denotes WEST
     * @return direction player is facing
     */
    public int getDirection() { return direction; }

    /**
     * Rotates player 90 degrees clockwise given amount of times.
     * @param amountToRotate denotes amount of times to rotate
     */
    public void rotate(int amountToRotate) { direction = (direction + amountToRotate) % 4; }
}
