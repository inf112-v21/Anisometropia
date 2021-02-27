package inf112.skeleton.app;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
    GameLogic gameLogic = new GameLogic(simpleGameMap);
    Player player = gameLogic.getCurrentPlayer();//

    //   TODO
    //  @BeforeEach


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
    public void doesPlayerRestoreHealthAfterPowerDown() {
        System.out.println(player.getDmgTokens());
        player.powerDown = true;
        player.updateDamageTokens();
        assertEquals(player.getDmgTokens(),0);
    }

    @Test
    public void testDoesPlayerLoseOneLifeIfDead() {
        int currentLives = player.getLifeTokens();
        player.playerDies();
        assertEquals(player.getLifeTokens(), currentLives-1);
    }

    @Test
    public void testCheckIfPlayerCanRespawn() {
        player.setLifeTokens(0);
        assertEquals(player.checkIfPlayerCanRespawn(), false);

        player.setLifeTokens(1);
        assertEquals(player.checkIfPlayerCanRespawn(), true);
    }


//    // Test does not currently work. Setup is somehow wrong
//    @Test
//    public void rotatingNorthFacingPlayerOneUnitLeavesPlayerFacingEast() {
//        player.rotate(1);
//
//        assertEquals(1, player.getDirection());
//    }
}