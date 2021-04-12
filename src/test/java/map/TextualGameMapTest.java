package map;

import actor.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextualGameMapTest {

    @Test
    public void testPlayerOnGameMap() {
        TextualGameMap textualGameMap = new TextualGameMap(12, 12);
        Player player = new Player(0, 0, "testPlayer", textualGameMap, true, 0);
        textualGameMap.setPlayerPosition(0,0, player);
        assertEquals(1, textualGameMap.getValue(0,0));
    }
}