package actor;

import cards.ProgramCard;
import map.GameMap;
import java.util.ArrayList;
import java.util.Collections;

public class Player implements IPlayer {
    GameMap gameMap;
    public int x, y, spawnX, spawnY;
    int direction; // 0 denotes NORTH, 1 denotes EAST, 2 denotes SOUTH, 3 denotes WEST
    int dmgTokens;
    int lifeTokens;
    public boolean isDead = false;
    public boolean isVictorious = false;
    public boolean[] flagsReached;
    public boolean isPoweredDown = false;
    public boolean hasAnnouncedPowerDown = false;
    public ArrayList<ProgramCard> dealtProgramCards;
    public ArrayList<ProgramCard> chosenProgramCards;
    public ArrayList<ProgramCard> lockedProgramCards;
    public String playerName;
    private final int characterID;
    private final boolean isAi;
    public boolean isLocal;

    public Player(int x, int y, String playerName, GameMap gameMap, boolean isLocal, int characterID) {
        this.x = this.spawnX = x;
        this.y = this.spawnY = y;
        this.playerName = playerName;
        this.direction = 0; // Initially faces NORTH
        this.gameMap = gameMap;
        this.isLocal = isLocal;
        this.characterID = characterID;
        dmgTokens = 0;
        lifeTokens = 3;
        flagsReached = new boolean[4];
        lockedProgramCards = new ArrayList<>(Collections.nCopies(5, new ProgramCard(0, 0, true)));

        isAi = false;
    }

    public Player(int x, int y, String playerName, GameMap gameMap, int characterID, boolean isAi) {
        this.x = this.spawnX = x;
        this.y = this.spawnY = y;
        this.playerName = playerName;
        this.direction = 0; // Initially faces NORTH
        this.gameMap = gameMap;
        this.characterID = characterID;
        dmgTokens = 0;
        lifeTokens = 3;
        flagsReached = new boolean[4];
        lockedProgramCards = new ArrayList<>(Collections.nCopies(5, new ProgramCard(0, 0, true)));

        this.isAi = isAi;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    //TODO: if a player moves: dx = 1 and dy = 0, we want player2 to move dx= -1 and dy=0 when they collide
    private boolean playersCollides(int dx, int dy){
        if (gameMap.isTherePlayerOnThisPosition(x+dx, y+dy)){
            //System.out.println("players collide");
            return true;
        }
        return false;
    }

    public void move(int dx, int dy) {
        if (canMove(dx, dy)) {
            gameMap.setToNull(x, y);
            gameMap.setPlayerPosition(x += dx, y += dy, this);

        }
    }

    public boolean canMove(int dx, int dy) {
        if(isPlayerDead()) return false;
        if (playersCollides(dx, dy)) return false;

        boolean wallOutOfPositionBlocked = false;
        boolean wallIntoPositionBlocked = false;
        if (gameMap.isThereWallOnThisPosition(this.getX(), this.getY()) ||
                gameMap.isTherePusherOnThisPosition(this.getX(), this.getY())) {
            if (!gameMap.getWall().checkOutOfWall(this.getX(), this.getY(), dx, dy)) {
                wallOutOfPositionBlocked = true;
            }
        }

        if (gameMap.isThereWallOnThisPosition(this.getX() + dx, this.getY() + dy) ||
                gameMap.isTherePusherOnThisPosition(this.getX() + dx, this.getY() + dy)) {
            if (!gameMap.getWall().checkIntoWall(this.getX(), this.getY(), dx, dy)) {
                wallIntoPositionBlocked = true;
            }
        }
        if(wallIntoPositionBlocked || wallOutOfPositionBlocked){
            return false;
        }
        return (x + dx >= 0 && x + dx < gameMap.getWidth()) && (y + dy >= 0 && y + dy < gameMap.getHeight());
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
            setDmgTokens(2);
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
        if (getDmgTokens()>=10)
            playerDies();
    }

    public void updateDamageTokens(int amountOfDamage) {
        setDmgTokens(Math.min((getDmgTokens() + amountOfDamage), 10));
        checkIfPlayerTooDamaged();
    }

    public void announcePowerDown() {
        hasAnnouncedPowerDown = true;
    }

    //TODO: the player should wait out one round if powered down.
    public void powerDownRobot() {
        isPoweredDown = true;
        setDmgTokens(0);
    }

    public void lockCards() {
        int numLockedCards = dmgTokens - 4;
        if (numLockedCards > 0) {
            for (int i = 4; i > 4-numLockedCards; i--) {
                chosenProgramCards.set(i, lockedProgramCards.get(i));
            }
        }
    }

    public void setDealtProgramCards(ArrayList<ProgramCard> dealtCards) { dealtProgramCards = dealtCards; }

    public void setChosenProgramCards(ArrayList<ProgramCard> chosenCards) {
        if (chosenProgramCards != null) {
            for (int i = 0; i < 5; i++) {
                lockedProgramCards.set(i, chosenProgramCards.get(i));
            }
        }
        chosenProgramCards = chosenCards;
        lockCards();
    }

    public ArrayList<ProgramCard> getDealtProgramCards() { return dealtProgramCards; }

    public ArrayList<ProgramCard> getChosenProgramCards() { return chosenProgramCards; }

    public void playerAlive() {
        isDead = false;
    }

    public void playerDies() {
        isDead = true;
        updateLifeTokens();
    }

    /**
     * changes checkpoint to player.
     */
    public void setNewCheckpoint(){
        spawnX = x;
        spawnY = y;
    }

    /**
     * Player is to draw an option card that gives the player new physics
     * (shooting lasers)
     * TODO: Implement optionCards and give new physics to player.
     */
    public void drawOptionCard(){
        System.out.println("player draws option card");
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

    public boolean getVictorious() { return isVictorious; }

    public int getCharacterID() {
        return characterID;
    }

    public boolean isAi() {
        return isAi;
    }

    public void startCardDecisionWithAI() {
        System.out.println("not an AI");
    }

}