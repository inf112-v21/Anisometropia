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
        addCardsToDeck("RegisterCardAssets/Move1.png", 1, true, 18);
        addCardsToUnique("RegisterCardAssets/Move1.png", 1, true);
        // Move 2
        addCardsToDeck("RegisterCardAssets/Move2.png", 2, true, 12);
        addCardsToUnique("RegisterCardAssets/Move2.png", 2, true);
        // Move 3
        addCardsToDeck("RegisterCardAssets/Move3.png", 3, true, 6);
        addCardsToUnique("RegisterCardAssets/Move3.png", 3, true);
        // Back up
        addCardsToDeck("RegisterCardAssets/BackUp.png", -1, true, 6);
        addCardsToUnique("RegisterCardAssets/BackUp.png", -1, true);
        // Rotate left
        addCardsToDeck("RegisterCardAssets/RotateLeft.png", 3, false, 18);
        addCardsToUnique("RegisterCardAssets/RotateLeft.png", 3, false);
        // Rotate right
        addCardsToDeck("RegisterCardAssets/RotateRight.png", 1, false, 18);
        addCardsToUnique("RegisterCardAssets/RotateRight.png", 1, false);
        // U-turn
        addCardsToDeck("RegisterCardAssets/UTurn.png", 2, false, 6);
        addCardsToUnique("RegisterCardAssets/UTurn.png", 2, false);
    }

    private void addCardsToUnique(String graphicLocation, int amountToMoveOrRotate, boolean movementCard) {
        uniqueCards.add(new RegisterCard(graphicLocation,amountToMoveOrRotate,movementCard));
    }

    private void addCardsToDeck(String graphicLocation, int amountToMoveOrRotate, boolean movementCard, int numOfCards) {
        for (int i = 0; i < numOfCards; i++) {
            deckOfCards.add(new RegisterCard(graphicLocation, amountToMoveOrRotate, movementCard));
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
