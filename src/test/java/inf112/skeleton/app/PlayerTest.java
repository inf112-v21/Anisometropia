package inf112.skeleton.app;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

public class PlayerTest {

    TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
    GameLogic gameLogic = new GameLogic(simpleGameMap);
    Player player = gameLogic.getCurrentPlayer();//

    //TODO @BeforeEach

    @Test
    public void testPlayer() {
        TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
        GameLogic gameLogic = new GameLogic(simpleGameMap);
        Player player1 = gameLogic.getCurrentPlayer();
        gameLogic.getPlayerQueue().next();
        Player player2 = gameLogic.getCurrentPlayer();
        gameLogic.getPlayerQueue().next();
        assertEquals(player1,gameLogic.getCurrentPlayer());
    }

    @Test
    public void testPlayerMoveXDirection() {
        int dx = 3;
        int currentXPos = player.getX();
        player.move(3, 0);
        int newXPos = player.getX();
        assertEquals(0, simpleGameMap.gridArray[player.getX()][currentXPos]);
        assertEquals(newXPos, currentXPos + dx);
    }

    @Test
    public void testPlayerMoveYDirection() {
        int dy = 3;
        int currentYPos = player.getY();
        player.move(0, dy);
        int newYPos = player.getY();
        assertEquals(0, simpleGameMap.gridArray[player.getX()][currentYPos]);
        assertEquals(newYPos, currentYPos + dy);
    }

    @Test
    public void rotatingNorthFacingPlayerOneUnitLeavesPlayerFacingEast() {
        player.rotate(1);
        assertEquals(1, player.getDirection());
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
        player.powerDown = true;
        player.updateDamageTokens();
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