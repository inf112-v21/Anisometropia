package assets;

import actor.Player;
import map.TextualGameMap;
import map.TextualGameMapTestAssets;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LaserTest {
    int x = 1;
    int y = 1;
    DamageAssets laser = new Laser();
    TextualGameMapTestAssets simpleGameMap = new TextualGameMapTestAssets(12, 12);
    Player player1 = new Player(x,y,"player1", simpleGameMap, true, 0);
    //Player player2 = new Player(x,y,"Player2",simpleGameMap,true,1);

    //this.gridArray[2][2] = DamageAssets.laserBeamHorizontal;
    //this.gridArray[3][2] = DamageAssets.laserBeamVertical;
    //this.gridArray[4][2] = DamageAssets.doubleLaserBeamHorizontal;
    //this.gridArray[5][2] = DamageAssets.doubleLaserBeamVertical;
    //this.gridArray[6][2] = DamageAssets.laserBeamCrossing;
    //this.gridArray[7][2] = DamageAssets.doubleLaserBeamCrossing;

    @Test
    public void isThereLaserOnPlayersPositionTest(){
        player1.x = 2; player1.y = 2;
        for (int i = 2; i <= 7 ; i++) {
            player1.x = i;
            assertTrue(simpleGameMap.isThereLaserBeamsOnThisPosition(player1.x, player1.y));
        }
    }
    @Test
    public void playerIsDamagedByLaserTest(){
        player1.x = 2; player1.y = 2;
        laser.updatePlayersHealth(player1,simpleGameMap);
        assertEquals(1, player1.getDmgTokens());
    }

}
