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

    public MultiPlayerLogic() { }

    /**
     * Checks if a multiplayer object has been created, if so, then there is a connection.
     * @return True or False
     */
    public boolean isConnected() {
        return mp!=null;
    }

    /**
     * Will run the multiplayer by using sendCards and receiveCards.
     * If it is the first turn of the game then player 1 should only send it´s own cards
     * because there are currently no cards to be received from the other players.
     * @throws IOException
     */
    public void runMultiPlayer() throws IOException {
        if(firstTurn == true)
            sendCards();
        else {
            sendCards();
            receiveCards();
        }
    }

    /**
     * Method for sending the current players chosen cards
     * @throws IOException
     */
    public void sendCards() throws IOException {
        firstTurn = false;
        DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
        String toSend = "";
        for(int i = 0; i <= 6; i++) {
            for(ProgramCard playerCard : gameLogic.getCurrentPlayer().getChosenProgramCards()) {
                if(deckOfProgramCards.uniqueCards.get(i).getCardType() == playerCard.getCardType()) {
                    toSend += playerCard.getCardType();
                }
            }
        }
        System.out.println("I am sending " + toSend);
        mp.send(toSend);
    }

    /**
     * Method for receiving the cards that the other players chosen cards
     * @throws IOException
     */
    private void receiveCards() throws IOException {
        DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
        String toReceive = mp.receive();
        ArrayList<ProgramCard> chosenCards = new ArrayList<>();
        for (int i = 0; i < toReceive.length(); i++) {
            int cardID = Character.getNumericValue(toReceive.charAt(i));
            chosenCards.add(deckOfProgramCards.uniqueCards.get(cardID));
        }
        gameLogic.getCurrentPlayer().setChosenProgramCards(chosenCards);
        gameLogic.getPlayerQueue().next();

        System.out.println("I am receiving " + chosenCards);
    }
}