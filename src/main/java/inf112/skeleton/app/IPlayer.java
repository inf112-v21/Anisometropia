package inf112.skeleton.app;

public interface IPlayer {

    /**
     *
     * @return the x position of the player
     */
    int getX();

    /**
     *
     * @return the y position of the player
     */
    int getY();

    /**
     * Moves the player in a given direction if that move is possible.
     * @param dx The amount to move in x direction
     * @param dy The amount to move in y direction
     */
    void move(int dx, int dy);

    /**
     * Checks whether or not it is possible to move in a given direction.
     * @param dx The amount to move in x direction
     * @param dy The amount to move in y direction
     * @return True or False
     */
    boolean canMove(int dx, int dy);

    /**
     * @return direction player is facing
     */
    int getDirection();

    /**
     * Rotates player 90 degrees clockwise given amount of times.
     * @param amountToRotate denotes amount of times to rotate
     */
    void rotate(int amountToRotate);

    /**
     * Moves player given amount of tiles in current facing direction.
     * @param amountToMove denotes amount of tiles to move
     * TODO: check whether player moves over deadly obstacle
     */
    void moveByDirection(int amountToMove);

    /**
     * Will set the amount of life tokens that a player has.
     * @param tokens
     */
    void setLifeTokens(int tokens);

    /**
     *
     * @return the current amount of life tokens a player has
     */
    int getLifeTokens();

    /**
     * If a player dies then this method will update the life tokens of this player accordingly.
     */
    void updateLifeTokens();

    /**
     * Determines whether a player is allowed to respawn or not.
     * @return If a player has more than 0 life tokens then true else false.
     */
    boolean checkIfPlayerCanRespawn();

    /**
     * If the player has more than 0 life tokens then the player will respawn with no damage tokens at the
     * starting position of the game.
     */
    void respawn();

    /**
     * Will set the amount of damage tokens that a player has.
     * @param tokens
     */
    void setDmgTokens(int tokens);

    /**
     *
     * @return the current amount of life tokens a player has.
     */
    int getDmgTokens();

    /**
     * If a player is damaged or if a player chooses to power down,
     * then this method will update the damage tokens of this player accordingly.
     * Also handles players that are too damaged to continue the game.
     */
    void updateDamageTokens();

    /**
     * Checks if a player is too damaged to continue the game. If a player reaches 10 damage tokens
     * then it should die.
     */
    void checkIfPlayerTooDamaged();

    /**
     * Sets Boolean isDead to false
     */
    void playerAlive();

    /**
     * Sets Boolean isDead to true and calls updateLifeTokens to remove one life token from the player that died.
     */
    void playerDies();

    /**
     * Checks if the player is dead or alive.
     * @return True or false
     */
    boolean isPlayerDead();

    /**
     * Sets Boolean isVictorious to true
     */
    void playerWins();

    /**
     * @return If the player has visited all the flags then true else false
     */
    boolean hasWon();

}
