package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Generates and maintains the full deck of register cards.
 */
public class DeckOfRegisterCards {
    private ArrayList<RegisterCard> deckOfCards;

    public DeckOfRegisterCards() {
        reshuffleDeck();
    }

    /**
     * Generates a full deck of register cards.
     */
    private void generateDeck() {
        // Move 1
        addCardsToDeck("RegisterCardAssets/Move1", 1, true, 18);

        // Move 2
        addCardsToDeck("RegisterCardAssets/Move2", 2, true, 12);

        // Move 3
        addCardsToDeck("RegisterCardAssets/Move3", 3, true, 6);

        // Back up
        addCardsToDeck("RegisterCardAssets/BackUp", -1, true, 6);

        // Rotate left
        addCardsToDeck("RegisterCardAssets/RotateLeft", 1, false, 18);

        // Rotate right
        addCardsToDeck("RegisterCardAssets/RotateRight", 3, false, 18);

        // U-turn
        addCardsToDeck("RegisterCardAssets/UTurn", 2, false, 6);
    }

    private void addCardsToDeck(String graphicLocation, int amountToMoveOrRotate, boolean movementCard, int numOfCards) {
        for (int i = 0; i < numOfCards; i++) {
            deckOfCards.add(new RegisterCard(graphicLocation, amountToMoveOrRotate, movementCard));
        }
    }

    private void shuffleDeck() { Collections.shuffle(deckOfCards); }

    public void reshuffleDeck() {
        generateDeck();
        shuffleDeck();
    }

    public ArrayList<RegisterCard> getDeck() {
        return deckOfCards;
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
