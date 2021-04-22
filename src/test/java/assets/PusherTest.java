package assets;

import actor.Player;
import map.TextualGameMap;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PusherTest {
    int x = 1;
    int y = 1;
    MovingAssets pusher = new Pusher();
    TextualGameMap simpleGameMap = new TextualGameMap(12, 12);
    Player player = new Player(x,y,"player", simpleGameMap, true, 0);
    //---FROM TEXTUAL GAMEMAP:----
    //    this.gridArray[11][2] = MovingAssets.pusherDown;
    //    this.gridArray[9][2] = MovingAssets.pusherUp;
    //    this.gridArray[10][2] = MovingAssets.pusherLeft;
    //    this.gridArray[10][4] = MovingAssets.pusherRight;

    @Test
    public void pusherPushesPlayerDownTest(){
        player.x = 11; player.y = 2;
        assertTrue(simpleGameMap.isTherePusherOnThisPosition(player.x, player.y));
    }
    @Test
    public void pusherPushesPlayerUpTest(){
        player.x = 9; player.y = 2;
        assertTrue(simpleGameMap.isTherePusherOnThisPosition(player.x, player.y));
    }
    @Test
    public void pusherPushesPlayerLeftTest(){
        player.x = 10; player.y = 2;
        assertTrue(simpleGameMap.isTherePusherOnThisPosition(player.x, player.y));
    }
    @Test
    public void pusherPushesPlayerRightTest(){
        player.x = 10; player.y = 4;
        assertTrue(simpleGameMap.isTherePusherOnThisPosition(player.x, player.y));
    }
}
