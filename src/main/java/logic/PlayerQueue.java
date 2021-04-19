package logic;

import actor.Player;

import java.util.ArrayList;

public class PlayerQueue {

    ArrayList<Player> playerList;
    public int turnCounter;

    public PlayerQueue() {
        playerList = new ArrayList<>();
        this.turnCounter = 0;
    }

    public ArrayList<Player> getPlayerQueue(){
        return playerList;
    }

    public void next() {
        turnCounter++;
        if (getCurrentPlayer().isPoweredDown) next();
    }

    public void add(Player player){
        this.playerList.add(player);
    }

    public Player getCurrentPlayer() { return playerList.get(turnCounter % playerList.size()); }

    /**
     * @return player in the last position of playerList
     */
    public Player getLastPlayerInQueue() {
        return playerList.get(playerList.size() - 1);
    }

    /**
     * @return last player in playerList which is not powered down.
     */
    public Player getLastPlayer() {
        return getLastPlayerHelper(1);
    }

    /**
     * Recursively looks for the last player in playerList which is not powered down.
     * @param recursionNumber denotes how far back in playerList we are.
     * @return last player in playerList which is not powered down.
     */
    public Player getLastPlayerHelper(int recursionNumber) {
        int queueIndex = playerList.size() - recursionNumber;
        if (queueIndex == 0) {
            return playerList.get(0);
        }
        else if (playerList.get(queueIndex).isPoweredDown) {
            return getLastPlayerHelper(++recursionNumber);
        }
        else {
            return playerList.get(queueIndex);
        }
    }

    public void setCurrentPlayerToFirstInQueue() {
        turnCounter = 0;
    }
}
