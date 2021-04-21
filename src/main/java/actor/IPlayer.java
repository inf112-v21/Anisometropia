package actor;

import cards.ProgramCard;
import logic.PlayerQueue;

import java.awt.*;
import java.util.ArrayList;

public interface IPlayer {

    /**
     * Will set the player queue.
     * @param playerQueue The queue to set.
     */
    void setPlayerQueue(PlayerQueue playerQueue);

    /**
     * @return the x position of the player
     */
    int getX();

    /**
     * @return the y position of the player
     */
    int getY();

    /**
     * Inspects given coordinates and looks for any players positioned there.
     * @param x x coordinate to inspect
     * @param y y coordinate to inspect
     * @return player found, otherwise null.
     */
    Player getPlayerByPos(int x, int y);

    /**
     * If any upgrades to laser - shoots multiple lasers.
     * Else fires of one laser beam in players faced direction.
     */
    void playerShootLaser();

    /**
     * Robot shoots a laser based of players direction, that can damage players and be stopped by walls.
     */
    void shootLaserBasedOnPlayersDirection();

    /**
     * Clears player's shot lasers from the board.
     */
    void clearPlayerShootLaserBoard();

    /**
     * Shows lasers on the board.
     */
    void placeLasers();

    /**
     * If current player(1) collides with a player(2), this player(2) is to be moved in the same direction as
     * the player moves.
     * Called in canMove.
     * @param dx distance to move in x direction
     * @param dy distance to move in y direction
     */
    void playersCollide(int dx, int dy);

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
     */
    void moveForward(int amountToMove);

    /**
     * Moves player one tile in desired direction.
     * @param desiredDirection direction faced by player
     */
    void moveByDirection(int desiredDirection);

    /**
     * Will set the amount of life tokens that a player has.
     * @param tokens number of tokens.
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
     * @param tokens number of tokens
     */
    void setDmgTokens(int tokens);

    /**
     *
     * @return the current amount of life tokens a player has.
     */
    int getDmgTokens();

    /**
     * If a player is damaged then this method will update the damage tokens of this player accordingly.
     * Also check if a player is too damaged to continue the game.
     * @param amountOfDamage How many damage tokens this player earns from getting damaged.
     */
    void updateDamageTokens(int amountOfDamage);

    /**
     * Checks if a player is too damaged to continue the game. If a player reaches 10 damage tokens
     * then it should die.
     */
    void checkIfPlayerTooDamaged();

    /**
     * Stores player's decision to power down their robot the following round.
     */
    void announcePowerDown();

    /**
     * Will power down the robot. When a robot is powered down it´s damage tokens is reset but the
     * robot is not allowed to make any moves for that round.
     */
    void powerDownRobot();

    /**
     * Inserts locked cards into list of chosen cards.
     */
    void lockCards();

    /**
     * Player is to draw an option card that gives the player new abilities.
     */
    void drawOptionCard();

    /**
     * Updates the player's upgrade.
     * @param upgrade to use.
     */
    void setUpgrade(String upgrade);

    /**
     * @return player's current upgrade
     */
    String getUpgrade();

    /**
     * Will set the dealt program cards the player was given for one round.
     * Different from round to round.
     * @param dealtCards The program cards dealt
     */
    void setDealtProgramCards(ArrayList<ProgramCard> dealtCards);

    /**
     * Will set the cards that the player chose from the dealt program cards for that round.
     * @param chosenCards The chosen cards of a player
     */
    void setChosenProgramCards(ArrayList<ProgramCard> chosenCards);

    /**
     *
     * @return the dealt program cards of a player.
     */
    ArrayList<ProgramCard> getDealtProgramCards();

    /**
     *
     * @return the chosen program cards of a player.
     */
    ArrayList<ProgramCard> getChosenProgramCards();

    /**
     * Sets Boolean isDead to false
     */
    void playerAlive();

    /**
     * Sets Boolean isDead to true and calls updateLifeTokens to remove one life token from the player that died.
     */
    void playerDies();

    /**
     * Changes checkpoint to player's current position.
     */
    void setNewCheckpoint();

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
    boolean hasReachedAllFlags();

    /**
     * @return whether player has won the game or not
     */
    boolean getVictorious();

    /**
     * @return ID of player's character graphic.
     */
    int getCharacterID();

    /**
     * @return whether Player is AI or human-controlled.
     */
    boolean isAi();

    /**
     * Prints a message to the command line.
     */
    void startCardDecisionWithAI();

}