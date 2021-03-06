package cards;

import actor.Player;

/**
 * Contains methods and variables that pertain to program cards.
 */
public class ProgramCard {
    public int cardType;
    public int amountToMoveOrRotate;
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

    public String toString() {
        switch (cardType) {
            case 0:
                return "Move 1";
            case 1:
                return "Move 2";
            case 2:
                return "Move 3";
            case 3:
                return "Back up";
            case 4:
                return "Rotate left";
            case 5:
                return "Rotate right";
            case 6:
                return "U-turn";
            default:
                return "Unexpected value: " + cardType;
        }
    }
}
