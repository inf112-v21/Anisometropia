package cards;

import actor.Player;

/**
 * Contains methods and variables that pertain to program cards.
 */
public class ProgramCard {
    int cardType;
    int amountToMoveOrRotate;
    boolean isMovementCard; // Denotes whether card affects movement or direction.

    public ProgramCard(int cardType, int amountToMoveOrRotate, boolean isMovementCard) {
        this.cardType = cardType;
        this.amountToMoveOrRotate = amountToMoveOrRotate;
        this.isMovementCard = isMovementCard;
    }

    /**
     * Executes the action of the program card
     * @param player is the player whose program card is executing.
     */
    public void executeProgram(Player player) {
        if(isMovementCard) { player.moveForward(amountToMoveOrRotate); }
        else { player.rotate(amountToMoveOrRotate); }
    }

    public int getCardType() { return cardType; }
}
