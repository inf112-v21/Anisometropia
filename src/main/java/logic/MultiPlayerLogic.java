package logic;

import cards.DeckOfProgramCards;
import cards.ProgramCard;
import p2p.Multiplayer;
import screens.OnNetSetupScreen;

import java.io.IOException;
import java.util.ArrayList;

public class MultiPlayerLogic {
    GameLogic gameLogic;
    public Multiplayer mp;
    public boolean firstTurn;
    public int numPlayers;
    public ArrayList<Boolean> playersReady = new ArrayList<>();

    public MultiPlayerLogic() {

    }

    /**
     * Checks if a multiplayer object has been created, if so, then there is a connection.
     * @return True or False
     */
    public boolean isConnected() {
        return mp != null;
    }

    /**
     * Method for sending the current players chosen cards
     */
    public void sendCards() {
        firstTurn = false;
        DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
        String toSend = "";
        for(ProgramCard playerCard : gameLogic.getCurrentPlayer().getChosenProgramCards()) {
            for(int i = 0; i <= 6; i++) {
                if(deckOfProgramCards.uniqueCards.get(i).getCardType() == playerCard.getCardType()) {
                    toSend += " "+playerCard.getCardType();
                }
            }
        }

        playersReady.set(OnNetSetupScreen.playerID, true);
        mp.setToSend("CARD "+OnNetSetupScreen.playerID+toSend);
    }

    /**
     * Method for receiving the other players chosen cards
     */
    public void receiveCards(String[] messageReceived) {
        DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
        ArrayList<ProgramCard> chosenCards = new ArrayList<>();

        int[] cards = new int[5];
        int counter = 0;
        for (int i = 2; i < messageReceived.length; i++) {
            cards[counter] = Integer.parseInt(messageReceived[i]);
            counter++;
        }

        for (int i = 0; i < 5; i++) {
            chosenCards.add(deckOfProgramCards.uniqueCards.get(cards[i]));
        }

        int indexOfPlayerToChange = Integer.parseInt(messageReceived[1]);

        playersReady.set(indexOfPlayerToChange, true);
        gameLogic.getPlayerQueue().getPlayerQueue().get(indexOfPlayerToChange).setChosenProgramCards(chosenCards);
    }

    /**
     * Sends message that player is intending to power down next round.
     */
    public void sendPowerDown() {
        mp.setToSend("POWER_DOWN " + OnNetSetupScreen.playerID);
    }

    /**
     * Receives message of player's intention to power down next round.
     * @param messageReceived contains playerID of relevant player.
     */
    public void receivePowerDown(String[] messageReceived) {
        int playerToPowerDown = Integer.parseInt(messageReceived[1]);
        gameLogic.getPlayerQueue().getPlayerQueue().get(playerToPowerDown).announcePowerDown();
    }

    /**
     * Sends drawn option card of the local player.
     */
    public void sendOptionCard() {
        mp.setToSend("OPTION_CARD " + OnNetSetupScreen.playerID + " " + gameLogic.getLocalPlayer().getUpgrade());
    }

    /**
     * Receives option card along with the player to assign it to.
     * @param messageReceived of format "playerID <option card>"
     */
    public void receiveOptionCard(String[] messageReceived) {
        int playerToUpgrade = Integer.parseInt(messageReceived[1]);
        String drawnOptionCard = messageReceived[2];
        gameLogic.getPlayerQueue().getPlayerQueue().get(playerToUpgrade).setUpgrade(drawnOptionCard);
    }

    /**
     * Checks if all players have chosen their desired cards.
     * @return boolean.
     */
    public boolean checkIfAllPlayersReady() {
        for (Boolean bool : playersReady) {
            if(!bool) return false;
        }
        return true;
    }

    public void setPlayersNotReady() {
        for (int i = 0; i < playersReady.size(); i++) {
            if (gameLogic.getPlayerQueue().getPlayerQueue().get(i).isPoweredDown) continue;
            playersReady.set(i, false);
        }
    }

    /**
     * Prepares variables to be used for assigning player IDs.
     */
    public void initializeID() {
        if (mp.isHosting()) {
            numPlayers = 1;
            OnNetSetupScreen.playerID = 0;
        }
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

}
