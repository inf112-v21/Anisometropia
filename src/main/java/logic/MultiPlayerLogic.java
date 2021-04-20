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
     * @throws IOException
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
        System.out.println("I am sending " + OnNetSetupScreen.playerID+toSend);
        mp.setToSend("CARD "+OnNetSetupScreen.playerID+toSend);
    }

    public boolean checkIfAllPlayersReady() {
        for (Boolean bool : playersReady) {
            if(!bool) return false;
        }
        return true;
    }

    public void setPlayersNotReady() {
        for (int i = 0; i < playersReady.size(); i++) {
            playersReady.set(i, false);
        }
    }

    /**
     * Method for receiving the other players chosen cards
     * @throws IOException
     */
    public void receiveCards(String[] messageReceived) throws IOException {
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

        String receivedCards = "";
        for(ProgramCard card : chosenCards) {
           receivedCards += card.getCardType();
        }

        System.out.println("I am receiving " + receivedCards);
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
