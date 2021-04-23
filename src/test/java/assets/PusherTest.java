package assets;

import actor.Player;
import map.TextualGameMap;
import map.TextualGameMapTestAssets;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PusherTest {
    int x = 1;
    int y = 1;
    MovingAssets pusher = new Pusher();
    TextualGameMapTestAssets simpleGameMap = new TextualGameMapTestAssets(12, 12);
    Player player = new Player(x,y,"player", simpleGameMap, true, 0);
    Player player2 = new Player(x,y, "player2", simpleGameMap, true, 1);
    //---FROM TEXTUAL GAMEMAP:----
    //    this.gridArray[10][9] = MovingAssets.pusherDown;
    //    this.gridArray[10][8] = MovingAssets.pusherUp;
    //    this.gridArray[10][7] = MovingAssets.pusherLeft;
    //    this.gridArray[10][6] = MovingAssets.pusherRight;

    @Test
    public void IsPlayerStandingOnAPusherTest(){
        player.x = 9; player.y = 10;
        assertTrue(simpleGameMap.isTherePusherOnThisPosition(player.x, player.y));
        player.x = 8; player.y = 10;
        assertTrue(simpleGameMap.isTherePusherOnThisPosition(player.x, player.y));
        player.x = 7; player.y = 10;
        assertTrue(simpleGameMap.isTherePusherOnThisPosition(player.x, player.y));
        player.x = 6; player.y = 10;
        assertTrue(simpleGameMap.isTherePusherOnThisPosition(player.x, player.y));
    }
}
