package assets;

import actor.Player;
import map.TextualGameMap;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepairTest {
    int x = 1;
    int y = 1;
    DamageAssets repairStation = new Repair();
    TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
    Player player = new Player(x,y,"player", simpleGameMap, true, 0);
    Player player2 = new Player(x,y, "player2", simpleGameMap, true, 1);
//    this.gridArray[5][5] = DamageAssets.wrench;
//    this.gridArray[5][6] = DamageAssets.doubleWrench;

    @Test
    public void wrenchRepairTest() {
        //---------SINGLE WRENCH-------
        player.x=5; player.y = 5;
        player.updateDamageTokens(1);
        repairStation.updatePlayersHealth(player, simpleGameMap);
        assertEquals(0, player.getDmgTokens());
    }

    @Test
    public void doubleWrenchRepairTest(){
        //---------DOUBLE WRENCH-------
        player2.x=5; player2.y=6;
        player2.updateDamageTokens(1);
        repairStation.updatePlayersHealth(player, simpleGameMap);
        assertEquals(0, player.getDmgTokens());
    }

    @Test
    public void ifNoRepairStationThenNoHealTest() {
        //---------SINGLE WRENCH-------
        player.x=6; player.y = 6;
        player.updateDamageTokens(1);
        repairStation.updatePlayersHealth(player, simpleGameMap);
        assertFalse(player.getDmgTokens() != 1);
    }

    @Test
    public void playerCanNotHaveNegativeAmountOfDamageTokens(){
        player.x = 5; player.y = 5;
        player.updateDamageTokens(0);
        repairStation.updatePlayersHealth(player, simpleGameMap);
        assertFalse(player.getDmgTokens()<0);
    }

    @Test
    public void isThereWrenchOnThisPositionTest(){
        player.x=5; player.y = 5;
        assertTrue(simpleGameMap.isThereRepairStationOnThisPosition(player.x, player.y));
        player2.x=5; player2.y = 6;
        assertTrue(simpleGameMap.isThereRepairStationOnThisPosition(player.x, player.y));

        player.x=7; player.y =6; //Not a repairStation on this position
        assertFalse(simpleGameMap.isThereRepairStationOnThisPosition(player.x, player.y));
    }
}
