package inf112.skeleton.app;

/**
 * Contains methods and variables that pertain to register cards.
 */
public class RegisterCard {
    String pictureLocation; // File location of card graphics
    int amountToMoveOrRotate;
    boolean movementCard; // Denotes whether card affects movement or direction
    Player player;

    public RegisterCard(String pictureLocation, int amountToMoveOrRotate, boolean movementCard) {
        this.pictureLocation = pictureLocation;
        this.amountToMoveOrRotate = amountToMoveOrRotate;
        this.movementCard = movementCard;
    }

    /**
     * Executes the action of the register card
     */
    public void executeRegister() {
        if(movementCard) { player.moveByDirection(amountToMoveOrRotate); }
        else { player.rotate(amountToMoveOrRotate); }
    }
}
