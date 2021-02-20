package inf112.skeleton.app;

import java.util.ArrayList;

public class PlayerQueue {

    ArrayList<Player> playerList;
    int index;

    public PlayerQueue() {
        playerList = new ArrayList<>();
        this.index = 0;
    }

    public ArrayList<Player> getPlayerQueue(){
        return playerList;
    }

    public void next() {
        index++;
    }

    public void add(Player player){
        this.playerList.add(player);
    }


    public Player getCurrentPlayer(){
        return playerList.get(index % playerList.size());
    }

}
