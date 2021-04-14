package logic;

import cards.DeckOfProgramCards;
import cards.ProgramCard;
import p2p.Multiplayer;

import java.io.IOException;
import java.util.ArrayList;

public class MultiPlayerLogic {
    GameLogic gameLogic;
    public Multiplayer mp;
    public boolean firstTurn;
    public int numPlayers;
    public int playerID; // Which index in PlayerQueue this session controls.

    public ArrayList<Boolean> playersReady = new ArrayList<>();

    public MultiPlayerLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    /**
     * Checks if a multiplayer object has been created, if so, then there is a connection.
     * @return True or False
     */
    public boolean isConnected() {
        return mp != null;
    }

    /**
     * Will run the multiplayer by using sendCards and receiveCards.
     * If it is the first turn of the game then player 1 should only send it´s own cards
     * because there are currently no cards to be received from the other players.
     * @throws IOException
     */
    public void runMultiPlayer() throws IOException {
//        sendCards();
//        receiveCards();
    }

    /**
     * Method for sending the current players chosen cards
     * @throws IOException
     */
    public void sendCards() throws IOException {
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

        System.out.println("I am sending " + toSend);
//        mp.send(toSend);
        mp.setToSend("CARD "+playerID+toSend);
    }

    public boolean checkIfAllPlayersReady() {
        for (Boolean bool : gameLogic.multiPlayerLogic.playersReady) {
            if(!bool) return false;
        }
        return true;
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

//        gameLogic.getCurrentPlayer().setChosenProgramCards(chosenCards);
//        gameLogic.getPlayerQueue().next();

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
            playerID = 0;
        }
    }
}
