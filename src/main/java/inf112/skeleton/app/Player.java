package inf112.skeleton.app;

import java.util.Arrays;

public class Player implements IPlayer {
    int x, y, spawnX, spawnY;
    int direction; // 0 denotes NORTH, 1 denotes EAST, 2 denotes SOUTH, 3 denotes WEST
    private int dmgTokens;
    private int lifeTokens;
    boolean powerDown = false;
    boolean playerGetsDamaged = false;
    boolean isDead = false;
    boolean isVictorious = false;
    boolean[] flagsReached;
    boolean conveyorBeltReached;
    GameMap gameMap;
    String playerName;

    public Player(int x, int y, String playerName, GameMap gameMap) {
        this.x = this.spawnX = x;
        this.y = this.spawnY = y;
        dmgTokens = 0;
        lifeTokens = 3;
        this.playerName = playerName;
        this.direction = 0; // Initially faces NORTH
        this.gameMap = gameMap;
        flagsReached = new boolean[4];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy) {
        if (canMove(dx, dy)) {
            gameMap.setToNull(x, y);
            gameMap.setPlayerPosition(x += dx, y += dy, this);
        }
    }

    //TODO Check for walls.
    public boolean canMove(int dx, int dy) {
        return (x+dx >= 0 && x+dx < gameMap.getWidth()) && (y+dy >= 0 && y+dy < gameMap.getHeight());
    }

    public int getDirection() { return direction; }

    public void rotate(int amountToRotate) { direction = (direction + amountToRotate) % 4; }

     // TODO: check whether player moves over deadly obstacle
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

    public void setLifeTokens(int tokens) {
        this.lifeTokens = tokens;
    }

    public int getLifeTokens() {
        return lifeTokens;
    }

    public void updateLifeTokens() {
        setLifeTokens(getLifeTokens()-1);
    }

    public boolean checkIfPlayerCanRespawn() {
        if (getLifeTokens() == 0)
            return false;
        return true;
    }

    public void respawn() {
        if (checkIfPlayerCanRespawn()) {
            Arrays.fill(flagsReached, Boolean.FALSE);
            gameMap.setToNull(x, y);
            setDmgTokens(0);
            this.x = spawnX;
            this.y = spawnY;
            this.direction = 0;
            isVictorious = false;
            playerAlive();
            gameMap.setPlayerPosition(spawnX, spawnY, this);
        }
    }

    public void setDmgTokens(int tokens) {
        this.dmgTokens = tokens;
    }

    public int getDmgTokens() {
        return dmgTokens;
    }

    public void checkIfPlayerTooDamaged() {
        if(getDmgTokens()==10)
            playerDies();
    }

    public void updateDamageTokens() {
        if (playerGetsDamaged)
            setDmgTokens(getDmgTokens() + 1);
        if (powerDown)  //If power down button pressed then this input will be received in game logic. Set player.powerDown to true if so to activate it and call this method.
            setDmgTokens(0);
        checkIfPlayerTooDamaged();
    }

    public void playerAlive() {
        isDead = false;
    }

    public void playerDies() {
        isDead = true;
        updateLifeTokens();
    }

    public boolean isPlayerDead() {
        return isDead;
    }

    public void playerWins() {
        isVictorious = true;
    }

    public boolean hasWon() {
        return(flagsReached[3]);
    }


}