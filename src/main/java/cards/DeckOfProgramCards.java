package cards;

import actor.Player;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Generates and maintains the full deck of program cards.
 */
public class DeckOfProgramCards {
    public final ArrayList<ProgramCard> deckOfCards = new ArrayList<>();
    public final ArrayList<ProgramCard> uniqueCards = new ArrayList<>();

    public DeckOfProgramCards() {
        initializeDeck();
    }

    /**
     * Generates a full deck of program cards.
     */
    private void generateDeck() {
        // Move 1
        addCardsToDeck(0, 1, true, 18);
        addCardsToUnique(0, 1, true);
        // Move 2
        addCardsToDeck(1, 2, true, 12);
        addCardsToUnique(1, 2, true);
        // Move 3
        addCardsToDeck(2, 3, true, 6);
        addCardsToUnique(2, 3, true);
        // Back up
        addCardsToDeck(3, -1, true, 6);
        addCardsToUnique(3, -1, true);
        // Rotate left
        addCardsToDeck(4, 3, false, 18);
        addCardsToUnique(4, 3, false);
        // Rotate right
        addCardsToDeck(5, 1, false, 18);
        addCardsToUnique(5, 1, false);
        // U-turn
        addCardsToDeck(6, 2, false, 6);
        addCardsToUnique(6, 2, false);
    }

    private void addCardsToUnique(int cardType, int amountToMoveOrRotate, boolean movementCard) {
        uniqueCards.add(new ProgramCard(cardType, amountToMoveOrRotate, movementCard));
    }

    private void addCardsToDeck(int cardType, int amountToMoveOrRotate, boolean movementCard, int numOfCards) {
        for (int i = 0; i < numOfCards; i++) {
            deckOfCards.add(new ProgramCard(cardType, amountToMoveOrRotate, movementCard));
        }
    }

    public ArrayList<ProgramCard> getDeckOfCards() {
        return deckOfCards;
    }

    private void shuffleDeck() { Collections.shuffle(deckOfCards); }

    public void initializeDeck() {
        generateDeck();
        shuffleDeck();
    }

    /**
     * Removes the first cards from deckOfCards and places them in new list, cards.
     * The number of cards removed depends on player's number of damage tokens.
     * @return list containing the program cards for a player to choose from.
     */
    public ArrayList<ProgramCard> dealCards(Player player) {
        ArrayList<ProgramCard> cards = new ArrayList<>();
        for (int i = 0; i < 9-player.getDmgTokens(); i++) {
            cards.add(deckOfCards.remove(0));
        }
        return cards;
    }
}
