package actor;

import assets.MovingAssets;
import assets.Pusher;
import cards.ProgramCard;
import logic.PlayerQueue;
import map.GameMap;

import java.awt.*;
import java.util.*;

public class Player implements IPlayer {
    MovingAssets pusher = new Pusher();
    GameMap gameMap;
    PlayerQueue playerQueue;
    public int x, y, spawnX, spawnY;
    int direction; // 0 denotes NORTH, 1 denotes EAST, 2 denotes SOUTH, 3 denotes WEST
    int dmgTokens;
    int lifeTokens;
    public boolean isDead = false;
    public boolean isVictorious = false;
    public boolean[] flagsReached;
    public boolean isPoweredDown = false;
    public boolean hasAnnouncedPowerDown = false;
    public String myUpgrade = "";
    public ArrayList<ProgramCard> dealtProgramCards;
    public ArrayList<ProgramCard> chosenProgramCards;
    public ArrayList<ProgramCard> lockedProgramCards;
    public String playerName;
    private final int characterID;
    private final boolean isAi;
    public boolean isLocal;

    private final ArrayList<Point> shootingLaserLocationsVertical = new ArrayList<>();
    private final ArrayList<Point> shootingLaserLocationsHorizontal = new ArrayList<>();
    public ArrayList<String> upgrades = new ArrayList<>();

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

        upgrades.add("doubleLaser");
        upgrades.add("shootBehind");
        upgrades.add("respawnWithZeroDamage");

        isAi = false;
    }

    public Player(int x, int y, String playerName, GameMap gameMap, boolean isLocal, int characterID, boolean isAi) {
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

        upgrades.add("doubleLaser");
        upgrades.add("shootBehind");
        upgrades.add("respawnWithZeroDamage");

        this.isAi = isAi;
    }

    public void setPlayerQueue(PlayerQueue playerQueue){
        this.playerQueue = playerQueue;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getPlayerByPos(int x, int y){
        for (int i = 0; i < playerQueue.getPlayerQueue().size(); i++) {
            Player playerByPos = playerQueue.getPlayerQueue().get(i);
            if (playerByPos.getX() == x && playerByPos.getY() == y){
                return playerByPos;
            }
        }
        return null;
    }

    public void playerShootLaser(){
        if (myUpgrade.equals("doubleLaser")){
            shootLaserBasedOnPlayersDirection();
        }
        if (myUpgrade.equals("shootBehind")){
            rotate(2);
            shootLaserBasedOnPlayersDirection();
            rotate(2);
        }
        if (myUpgrade.equals("sideLasers")){
            rotate(1);
            shootLaserBasedOnPlayersDirection();
            rotate(2);
            shootLaserBasedOnPlayersDirection();
            rotate(1);
        }
        shootLaserBasedOnPlayersDirection();
    }


    /**
     * Shoots a laser in the direction a player is facing
     */
    private void shootLaserBasedOnPlayersDirection(){
//-------------if Player faces NORTH or SOUTH----------
        if (getDirection() == 0){ //NORTH
            for (int i = 0 ; y + i < gameMap.getHeight(); i++ ){
                if (i!=0){
                    Point coordinates = new Point(x, y+i);
                    shootingLaserLocationsVertical.add(coordinates);
                }
                if (i != 0 && gameMap.isTherePlayerOnThisPosition(x, y + i)){
                    getPlayerByPos(x, y + i ).updateDamageTokens(1);
                    break;
                }
                if (!gameMap.getWall().checkIntoWall(x,y+i,0, 1) || !gameMap.getWall().checkOutOfWall(x,y+i,0, 1)){
                    break;
                }
            }
        }
        if (getDirection() == 2 ) {//SOUTH
            for (int i = 0; y - i >= 0; i++ ){ //height = 0
                if (i!=0){
                    Point coordinates = new Point(x, y-i);
                    shootingLaserLocationsVertical.add(coordinates);
                }
                if (i !=0 && gameMap.isTherePlayerOnThisPosition(x,y - i)){
                    getPlayerByPos(x, y - i).updateDamageTokens(1);
                    break; //A laser wont travel through Players
                }
                if (!gameMap.getWall().checkIntoWall(x,y-i,0, -1) || !gameMap.getWall().checkOutOfWall(x,y-i,0, -1)){
                    break; //A Laser wont Travel through Walls
                }
            }
        }
// -------------if Player faces WEST or EAST------------
        if (getDirection() == 1 ) {//EAST
            for (int i = 0; x + i < gameMap.getWidth(); i++ ){ //height = 0

                if (i!=0){
                    Point coordinates = new Point(x+i, y);
                    shootingLaserLocationsHorizontal.add(coordinates);
                }
                if (i != 0 && gameMap.isTherePlayerOnThisPosition(x+i,y)){
                    getPlayerByPos(x+i,y).updateDamageTokens(1);
                    break;
                }
                if (!gameMap.getWall().checkIntoWall(x+i,y,1, 0) || !gameMap.getWall().checkOutOfWall(x+i,y,1, 0)){
                    break;
                }
            }
        }
        if (getDirection() == 3 ) {//WEST
            for (int i = 0; x - i >= 0; i++ ){ //height = 0
                if (i!=0){
                    Point coordinates = new Point(x-i, y);
                    shootingLaserLocationsHorizontal.add(coordinates);
                }
                if (i != 0 && gameMap.isTherePlayerOnThisPosition(x-i,y)){
                    getPlayerByPos(x-i,y).updateDamageTokens(1);
                    break;
                }
                if (!gameMap.getWall().checkIntoWall(x-i,y,-1, 0) || !gameMap.getWall().checkOutOfWall(x-i,y,-1, 0)){
                    break;
                }
            }
        }
    }

    public void clearPlayerShootLaserBoard(){
        if (shootingLaserLocationsVertical.size() > 0 || shootingLaserLocationsHorizontal.size() > 0) {
            for (int i = 0; i < shootingLaserLocationsHorizontal.size(); i++) {
                gameMap.setCell(shootingLaserLocationsHorizontal.get(i).x, shootingLaserLocationsHorizontal.get(i).y, "PlayerShootLaser", null);
            }
            for (int i = 0; i < shootingLaserLocationsVertical.size(); i++) {
                gameMap.setCell(shootingLaserLocationsVertical.get(i).x, shootingLaserLocationsVertical.get(i).y, "PlayerShootLaser", null);
            }
        }
        shootingLaserLocationsHorizontal.clear();
        shootingLaserLocationsVertical.clear();
    }

    public void placeLasers(){
        int horizontal = 0;
        int vertical = 2;
        if (myUpgrade.equals("doubleLaser")){
            horizontal = 1;
            vertical = 3;
        }
        for (int i = 0; i < shootingLaserLocationsHorizontal.size(); i++) {
            gameMap.setCell(shootingLaserLocationsHorizontal.get(i).x, shootingLaserLocationsHorizontal.get(i).y ,"PlayerShootLaser", gameMap.getLaserCell(horizontal));
        }
        for (int i = 0; i < shootingLaserLocationsVertical.size(); i++) {
            gameMap.setCell(shootingLaserLocationsVertical.get(i).x, shootingLaserLocationsVertical.get(i).y ,"PlayerShootLaser", gameMap.getLaserCell(vertical));
        }
    }

    /**
     * if current player(1) collides with a player(2), this player(2) is to be moved in the same direction as
     * the player moves.
     * Called in canMove.
     * @param dx distance to move in x direction
     * @param dy distance to move in y direction
     */
    private void playersCollide(int dx, int dy){
        for (int i = 0; i < playerQueue.getPlayerQueue().size(); i++) {
            Player pushedPlayer = playerQueue.getPlayerQueue().get(i);

            if (pushedPlayer.getX() == (x + dx) && pushedPlayer.getY() == (y + dy)) {
                if (pushedPlayer.canMove(dx, dy)) {
                    pushedPlayer.move(dx, dy);
                }else {
                    move(-dx,-dy);
                }
            }
        }
    }

    public void move(int dx, int dy) {
        if (canMove(dx, dy)) {
            gameMap.setToNull(x, y);
            gameMap.setPlayerPosition(x += dx, y += dy, this);

            //-------COMMENT THIS OUT WHEN RUNNING PLAYER TEST-----
            pusher.playerIsToMove(this, gameMap);
        }
    }

    public boolean canMove(int dx, int dy) {
        //Player cant move if he's dead
        if(isPlayerDead()) {
            return false;
        }

        //Players can't jump over holes
        if(gameMap.isThereHoleOnThisPosition(x,y)){
            return false;
        }

        //Player can't walk through walls
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
        }else {
            // Players can collide as long as there are no walls on the given position.
            if (gameMap.isTherePlayerOnThisPosition(x+dx,y+dy)){
                playersCollide(dx, dy);
            }
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
            if (myUpgrade.equals("respawnWithZeroDamage")){
                myUpgrade = "";
                setDmgTokens(0);
            }else {setDmgTokens(2);}
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

    public void drawOptionCard(){
        int randomUpgradeIndex = (int) (Math.random() * upgrades.size());
        myUpgrade = upgrades.get(randomUpgradeIndex);
        System.out.println("Here is your upgrade: " + myUpgrade);
    }

    public void setUpgrade(String upgrade) { myUpgrade = upgrade; }

    public String getUpgrade() {
        return myUpgrade;
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

    public boolean isPlayerDead() {
        return isDead;
    }

    public void playerWins() {
        isVictorious = true;
    }

    public boolean hasReachedAllFlags() { return flagsReached[3]; }

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