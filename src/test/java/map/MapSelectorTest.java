package map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MapSelectorTest {

    @Test
    public void mapChangeRightClickedTest() {
        MapSelector mapSelector = new MapSelector();
        String firstMap = mapSelector.getCurrentMap();
        mapSelector.mapChangeRightClicked();
        String secondMap = mapSelector.getCurrentMap();
        assertNotEquals(firstMap, secondMap);
    }

    @Test
    public void mapChangeLeftClickedTest() {
        MapSelector mapSelector = new MapSelector();
        String firstMap = mapSelector.getCurrentMap();
        mapSelector.mapChangeLeftClicked();
        String secondMap = mapSelector.getCurrentMap();
        assertNotEquals(firstMap, secondMap);
    }

    @Test
    public void setAndGetCurrentMapTest() {
        MapSelector mapSelector = new MapSelector();
        mapSelector.setCurrentMap("Testing");
        assertEquals(mapSelector.getCurrentMap(), "Testing");
        mapSelector.setCurrentMap("SecondTesting");
        assertNotEquals(mapSelector.getCurrentMap(), "Testing");
    }

}
