package actor;

import map.TextualGameMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
    Player player = new Player(1,1,"player", simpleGameMap, true, 0);
    Player player2 = new Player(3,1, "player2", simpleGameMap, true, 0);

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
        System.out.println(player.getY());
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
        player.updateDamageTokens(1);
        assertEquals(player.getDmgTokens(), currentHealth + 1);
    }


    
    @Test
    public void checkIfPlayerDiesBecauseOfTooMuchDamage() {
        player.setDmgTokens(9);
        player.setLifeTokens(3);
        int currentLife = player.getLifeTokens();
        player.updateDamageTokens(1);
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
    public void testOutOfBounds() {
        player.x =0; player.y = 0;
        assertFalse(player.canMove(0,-1));
        assertFalse(player.canMove(-1,0));
        player.x = 11;player.y = 11;
        assertFalse(player.canMove(0,1));
        assertFalse(player.canMove(1,0));

    }

    @Test
    //Checks if a player draws an existing option card
    public void testDrawOptionCard(){
        player.drawOptionCard();
        assertTrue(player.upgrades.contains(player.getUpgrade()));
        assertTrue(player.upgrades.size()>0);
    }

}