package inf112.skeleton.app;

/**
 * Contains methods and variables that pertain to register cards.
 */
public class RegisterCard {
    String graphicLocation; // File location of card graphics
    int amountToMoveOrRotate;
    boolean isMovementCard; // Denotes whether card affects movement or direction.

    public RegisterCard(String graphicLocation, int amountToMoveOrRotate, boolean isMovementCard) {
        this.graphicLocation = graphicLocation;
        this.amountToMoveOrRotate = amountToMoveOrRotate;
        this.isMovementCard = isMovementCard;
    }

    /**
     * Executes the action of the register card
     * @param player is the player whose register card is executing.
     */
    public void executeRegister(Player player) {
        if(isMovementCard) { player.moveByDirection(amountToMoveOrRotate); }
        else { player.rotate(amountToMoveOrRotate); }
    }

    public String getGraphicLocation() { return graphicLocation; }
}
