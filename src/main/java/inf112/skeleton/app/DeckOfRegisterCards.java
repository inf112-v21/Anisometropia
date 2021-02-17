package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Generates and maintains the full deck of register cards.
 */
public class DeckOfRegisterCards {
    private ArrayList<RegisterCard> deckOfCards;

    public DeckOfRegisterCards() {
        generateDeck();
        shuffleDeck();
    }

    /**
     * Generates a full deck of register cards.
     */
    public void generateDeck() {
        // Move 1
        for (int i = 0; i < 18; i++) {
            deckOfCards.add(new RegisterCard("RegisterCardAssets/Move1", 1, true));
        }
        // Move 2
        for (int i = 0; i < 12; i++) {
            deckOfCards.add(new RegisterCard("RegisterCardAssets/Move2", 2, true));
        }
        // Move 3
        for (int i = 0; i < 6; i++) {
            deckOfCards.add(new RegisterCard("RegisterCardAssets/Move3", 3, true));
        }
        // Back up
        for (int i = 0; i < 6; i++) {
            deckOfCards.add(new RegisterCard("RegisterCardAssets/BackUp", -1, true));
        }
        // Rotate left
        for (int i = 0; i < 18; i++) {
            deckOfCards.add(new RegisterCard("RegisterCardAssets/RotateLeft", 1, false));
        }
        // Rotate right
        for (int i = 0; i < 18; i++) {
            deckOfCards.add(new RegisterCard("RegisterCardAssets/RotateRight", 3, false));
        }
        // U-turn
        for (int i = 0; i < 6; i++) {
            deckOfCards.add(new RegisterCard("RegisterCardAssets/UTurn", 2, false));
        }

    }

    public void shuffleDeck() {
        Collections.shuffle(deckOfCards);
    }

    public ArrayList<RegisterCard> getDeck() {
        return deckOfCards;
    }
}
