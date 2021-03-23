package cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Generates and maintains the full deck of register cards.
 */
public class DeckOfRegisterCards {
    public final ArrayList<RegisterCard> deckOfCards = new ArrayList<>();
    public final ArrayList<RegisterCard> uniqueCards = new ArrayList<>();

    public DeckOfRegisterCards() {
        initializeDeck();
    }

    /**
     * Generates a full deck of register cards.
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

    private void addCardsToUnique(int cardIndex, int amountToMoveOrRotate, boolean movementCard) {
        uniqueCards.add(new RegisterCard(cardIndex, amountToMoveOrRotate, movementCard));
    }

    private void addCardsToDeck(int cardIndex, int amountToMoveOrRotate, boolean movementCard, int numOfCards) {
        for (int i = 0; i < numOfCards; i++) {
            deckOfCards.add(new RegisterCard(cardIndex, amountToMoveOrRotate, movementCard));
        }
    }

    public ArrayList<RegisterCard> getDeckOfCards() {
        return deckOfCards;
    }

    private void shuffleDeck() { Collections.shuffle(deckOfCards); }

    public void initializeDeck() {
        generateDeck();
        shuffleDeck();
    }

    /**
     * Removes the first nine cards from deckOfCards and places them in new list nineCards.
     * @return nineCards containing the nine register cards for a player to choose from.
     * TODO: deal cards relative to damage tokens
     */
    public ArrayList<RegisterCard> dealNineCards() {
        ArrayList<RegisterCard> nineCards = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            nineCards.add(deckOfCards.remove(0));
        }
        return nineCards;
    }
}
