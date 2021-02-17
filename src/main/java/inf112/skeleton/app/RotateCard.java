package inf112.skeleton.app;

/**
 * Contains methods and variables that pertain to register cards that affect rotation.
 */
public class RotateCard {
    String pictureLocation;
    int amountToRotate;
    Player player;

    public RotateCard(String pictureLocation, int distanceToMove) {
        this.pictureLocation = pictureLocation;
        this.amountToRotate = distanceToMove;
    }

    public void executeRegister() {
        System.out.println();
        player.rotate(amountToRotate);
    }
}
