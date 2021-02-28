package inf112.skeleton.app;

public interface IPlayer {

    int getX();

    int getY();

    void move(int dx, int dy);

    boolean canMove(int dx, int dy);


    /**
     * @return direction player is facing
     */
    int getDirection();

    /**
     * Rotates player 90 degrees clockwise given amount of times.
     * @param amountToRotate denotes amount of times to rotate
     */
    public void rotate(int amountToRotate);


    /**
     * Moves player given amount of tiles in current facing direction.
     * @param amountToMove denotes amount of tiles to move
     * TODO: check whether player moves over deadly obstacle
     */
    void moveByDirection(int amountToMove);

    void setLifeTokens(int tokens);

    int getLifeTokens();

    void updateLifeTokens();

    boolean checkIfPlayerCanRespawn();

    void setDmgTokens(int tokens);

    int getDmgTokens();

    void updateDamageTokens();

    void checkIfPlayerTooDamaged();

    void playerAlive();

    void playerDies();

    boolean isPlayerDead();

    void playerWins();

    boolean hasWon();







}
