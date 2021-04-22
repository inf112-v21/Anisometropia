package assets;

import actor.Player;
import logic.GameLogic;
import map.GameMap;
import map.TextualGameMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class GearTest {
    int x = 1;
    int y = 1;
    MovingAssets gear = new Gear();
    TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
    Player player1 = new Player(x,y,"player1", simpleGameMap, true, 0);
    //Player player2 = new Player(x,y,"Player2",simpleGameMap,true,1);
    //---FROM TEXTUAL GAMEMAP:----
    //this.gridArray[11][11] = MovingAssets.gearRotatingLeft; //GEAR LEFT
    //this.gridArray[10][11] = MovingAssets.gearRotatingRight;//GEAR RIGHT

    @Test
    public void playerRotatesToTheLeftTest(){
        player1.x = 11; player1.y = 11;
        gear.playerIsToMove(player1,simpleGameMap);
        //assertTrue(simpleGameMap.isThereGearOnThisPosition(player1.x, player1.y));
        assertTrue(player1.getDirection()==3);
    }
    @Test
    public void playerRotatesToTheRightTest(){
        player1.x=10; player1.y=10;
        //assertTrue(simpleGameMap.isThereGearOnThisPosition(player2.x, player2.y));
        gear.playerIsToMove(player1, simpleGameMap);
        assertTrue(player1.getDirection()==1);
    }
    @Test
    public void isThereGearOnThisPos(){
        player1.x = 11; player1.y = 11;
        assertTrue(simpleGameMap.isThereGearOnThisPosition(player1.x, player1.y));

        player1.x=10; player1.y=10;
        assertTrue(simpleGameMap.isThereGearOnThisPosition(player1.x, player1.y));
    }
}
