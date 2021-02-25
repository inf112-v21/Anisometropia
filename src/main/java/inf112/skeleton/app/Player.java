package inf112.skeleton.app;

import java.util.Arrays;

public class Player {
    int x, y, spawnX, spawnY;
    int direction; // 0 denotes NORTH, 1 denotes EAST, 2 denotes SOUTH, 3 denotes WEST
    private int dmgTokens;
    private int lifeTokens;

    boolean powerDown = false;
    boolean playerGetsDamaged = false;
    boolean isDead = false;
    boolean isVictorious = false;
    boolean[] flagsReached;
    boolean conveyorBeltReached = true;
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

    public boolean canMove(int dx, int dy) {
        return (x+dx >= 0 && x+dx < gameMap.getWidth()) && (y+dy >= 0 && y+dy < gameMap.getHeight());
    }

    public void move(int dx, int dy) {
        System.out.println("calling move with "+dx+" - "+dy);
        if (canMove(dx, dy)) {
            System.out.println("player can move!");
            gameMap.setToNull(x, y);
            gameMap.setPlayerPosition(x += dx, y += dy, this);
        }
    }

    /**
     * Moves player given amount of tiles in current facing direction.
     * @param amountToMove denotes amount of tiles to move
     * TODO: check whether player moves over deadly obstacle
     */
    public void moveByDirection(int amountToMove) {
        System.out.println("move by direction called, with "+amountToMove+ " amount");
        System.out.println("get direction= "+getDirection());
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
        Arrays.fill(flagsReached, Boolean.FALSE);
        gameMap.setToNull(x, y);
        this.x=spawnX;
        this.y=spawnY;
        this.direction = 0;
        isVictorious = false;
        playerAlive();
        gameMap.setPlayerPosition(spawnX, spawnY,this);
    }

    public boolean isPlayerDead() {
        return isDead;
    }

    public void playerWins() {
        isVictorious = true;
    }

    public boolean hasWon(){
        return flagsReached[3];
    }

    public void playerAlive() {
        isDead = false;
    }

    public void playerDies() {
        isDead = true;
        updateLifeTokens();
    }

    public void setLifeTokens(int tokens) {
        this.lifeTokens = tokens;
    }

    public int getLifeTokens() {
        return lifeTokens;
    }

    public void updateLifeTokens() {
            setLifeTokens(getLifeTokens()-1);
            checkIfPlayerCanRespawn();
        }

    public boolean checkIfPlayerCanRespawn() {
        return (getLifeTokens() == 0);
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * @return direction player is facing
     */
    public int getDirection() { return direction; }

    /**
     * Rotates player 90 degrees clockwise given amount of times.
     * @param amountToRotate denotes amount of times to rotate
     */
    public void rotate(int amountToRotate) { direction = (direction + amountToRotate) % 4; }
}