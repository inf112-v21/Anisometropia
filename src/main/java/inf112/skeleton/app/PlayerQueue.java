package inf112.skeleton.app;

import java.util.ArrayList;

public class PlayerQueue {

    ArrayList<Player> playerList;
    int turnCounter;

    public PlayerQueue() {
        playerList = new ArrayList<>();
        this.turnCounter = 0;
    }

    public ArrayList<Player> getPlayerQueue(){
        return playerList;
    }

    public void next() {
        turnCounter++;
    }

    public void add(Player player){
        this.playerList.add(player);
    }

    public Player getCurrentPlayer(){
        return playerList.get(turnCounter % playerList.size());
    }

    public Player getLastPlayer() { return playerList.get(playerList.size() - 1); }

}
