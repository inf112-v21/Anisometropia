package actor;

import cards.RegisterCard;
import map.GameMap;

import java.util.ArrayList;
import java.util.Arrays;

public class Player implements IPlayer {
    int x, y, spawnX, spawnY;
    int direction; // 0 denotes NORTH, 1 denotes EAST, 2 denotes SOUTH, 3 denotes WEST
    int dmgTokens;
    int lifeTokens;
    public boolean powerDown = false;
    public boolean playerGetsDamaged = false;
    public boolean isDead = false;
    public boolean isVictorious = false;
    public boolean[] flagsReached;
    public boolean conveyorBeltReached = true;
    public ArrayList<RegisterCard> dealtRegisterCards;
    public ArrayList<RegisterCard> chosenRegisterCards;
    GameMap gameMap;
    public String playerName;

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

    public void moveForward(int amountToMove) {
        if (amountToMove == -1) moveByDirection((direction + 2) % 4);
        for (int i = 0; i < amountToMove; i++) {
            moveByDirection(direction);
            if (isPlayerDead()) break;
        }
    }

    public void moveByDirection(int desiredDirection){
        switch(desiredDirection) {
            case 0:
                move(0, 1);
                break;
            case 1:
                move(1, 0);
                break;
            case 2:
                move(0, -1);
                break;
            case 3:
                move(-1, 0);
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
        return getLifeTokens() != 0;
    }

    public void respawn() {
        if (checkIfPlayerCanRespawn()) {
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

    public void setDealtRegisterCards(ArrayList<RegisterCard> dealtCards) { dealtRegisterCards = dealtCards; }

    public void setChosenRegisterCards(ArrayList<RegisterCard> chosenCards) { chosenRegisterCards = chosenCards; }

    public ArrayList<RegisterCard> getDealtRegisterCards() { return dealtRegisterCards; }

    public ArrayList<RegisterCard> getChosenRegisterCards() { return chosenRegisterCards; }

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

    public Boolean getVictorious() {
        return isVictorious;
    }

}