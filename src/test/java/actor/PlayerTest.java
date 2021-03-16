package actor;

import actor.Player;
import logic.GameLogic;
import logic.PlayerQueue;
import map.TextualGameMap;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class PlayerTest {

    TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
    Player player = new Player(1,1,"player", simpleGameMap);

    @Test
    public void testPlayerMoveXDirection() {
        int dx = 3;
        int currentXPos = player.getX();
        player.move(3, 0);
        int newXPos = player.getX();
        assertEquals(0, simpleGameMap.getValue(player.getX(), currentXPos));
        assertEquals(newXPos, currentXPos + dx);
    }

    @Test
    public void testPlayerMoveYDirection() {
        int dy = 3;
        int currentYPos = player.getY();
        player.move(0, dy);
        int newYPos = player.getY();
        assertEquals(0, simpleGameMap.getValue(player.getX(), currentYPos));
        assertEquals(newYPos, currentYPos + dy);
    }

    @Test
    public void rotatingNorthFacingPlayerOneUnitLeavesPlayerFacingEast() {
        player.rotate(1);
        assertEquals(1, player.getDirection());
    }

    @Test
    public void rotatingNorthFacingPlayerTwoUnitsLeavesPlayerFacingSouth() {
        player.rotate(2);
        assertEquals(2, player.getDirection());
    }

    @Test
    public void rotatingSouthFacingPlayerThreeUnitsLeavesPlayerFacingEast() {
        player.direction = 2;

        player.rotate(3);
        assertEquals(1, player.getDirection());
    }

    @Test
    public void movingPlayerNorthLeavesPlayerOneTileNorthOfStartingPosition(){
        int currentXPos = player.getX();
        int currentYPos = player.getY();
        int directionToMove = 0;

        player.moveByDirection(directionToMove);

        assertEquals(player.getX(), currentXPos);
        assertEquals(player.getY(), currentYPos + 1);
    }

    @Test
    public void movingPlayerEastLeavesPlayerOneTileEastOfStartingPosition(){
        int currentXPos = player.getX();
        int currentYPos = player.getY();
        int directionToMove = 1;

        player.moveByDirection(directionToMove);

        assertEquals(player.getX(), currentXPos + 1);
        assertEquals(player.getY(), currentYPos);
    }

    @Test
    public void movingPlayerSouthLeavesPlayerOneTileSouthOfStartingPosition(){
        int currentXPos = player.getX();
        int currentYPos = player.getY();
        int directionToMove = 2;

        player.moveByDirection(directionToMove);

        assertEquals(player.getX(), currentXPos);
        assertEquals(player.getY(), currentYPos - 1);
    }

    @Test
    public void movingPlayerWestLeavesPlayerOneTileWestOfStartingPosition(){
        int currentXPos = player.getX();
        int currentYPos = player.getY();
        int directionToMove = 3;

        player.moveByDirection(directionToMove);

        assertEquals(player.getX(), currentXPos - 1);
        assertEquals(player.getY(), currentYPos);
    }

    @Test
    public void movingNorthFacingPlayerForwardOneTile() {
        int currentYPos = player.getY();
        player.moveForward(1);
        assertEquals(player.getY(), currentYPos + 1);
    }

    @Test
    public void movingNorthFacingPlayerForwardTwoTiles() {
        int currentYPos = player.getY();
        player.moveForward(2);
        assertEquals(player.getY(), currentYPos + 2);
    }

    @Test
    public void movingNorthFacingPlayerForwardThreeTiles() {
        int currentYPos = player.getY();
        player.moveForward(3);
        assertEquals(player.getY(), currentYPos + 3);
    }

    @Test
    public void movingNorthFacingPlayerBackOneTile() {
        int currentYPos = player.getY();
        player.moveForward(-1);
        assertEquals(player.getY(), currentYPos - 1);
    }

    @Test
    public void testDoesPlayerGetDamagedIfHurt() {
        int currentHealth = player.getDmgTokens();
        player.playerGetsDamaged = true;
        player.updateDamageTokens();
        assertEquals(player.getDmgTokens(), currentHealth + 1);
    }

    @Test
    public void dontUpdateDamageTokensIfPlayerNotHurt() {
        int currentHealth = player.getDmgTokens();
        player.playerGetsDamaged = false;
        player.updateDamageTokens();
        assertEquals(player.getDmgTokens(), currentHealth);
    }
    
    @Test
    public void checkIfPlayerDiesBecauseOfTooMuchDamage() {
        player.setDmgTokens(9);
        player.setLifeTokens(3);
        int currentLife = player.getLifeTokens();
        player.playerGetsDamaged = true;
        player.updateDamageTokens();
        assertEquals(player.getLifeTokens(),currentLife - 1);
        assertTrue(player.isPlayerDead());
    }

    @Test
    public void doesPlayerRestoreHealthAfterPowerDown() {
       player.powerDownRobot();
        assertEquals(player.getDmgTokens(),0);
    }

    @Test
    public void testDoesPlayerLoseOneLifeTokenIfDead() {
        int currentLives = player.getLifeTokens();
        player.playerDies();
        assertEquals(player.getLifeTokens(), currentLives-1);
    }

    @Test
    public void testCheckIfPlayerCanRespawn() {
        player.setLifeTokens(0);
        assertFalse(player.checkIfPlayerCanRespawn());

        player.setLifeTokens(1);
        assertTrue(player.checkIfPlayerCanRespawn());
    }
    @Test
    //tests if the player can move out of Bounds
    public void testOutOfBounds(){
        assertFalse(player.canMove(0,13));
        assertFalse(player.canMove(0,-13));
        assertFalse(player.canMove(13,0));
        assertFalse(player.canMove(-13,0));
    }

}