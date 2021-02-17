package inf112.skeleton.app;

/**
 * Contains methods and variables that pertain to register cards that affect movement.
 */
public class MoveCard {
    String pictureLocation;
    int distanceToMove;
    Player player;

    public MoveCard(String pictureLocation, int distanceToMove) {
        this.pictureLocation = pictureLocation;
        this.distanceToMove = distanceToMove;
    }

    public void executeRegister() {
        player.move(0, distanceToMove);
    }
}
