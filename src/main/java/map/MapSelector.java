package map;

public class MapSelector {
    private String[] maps = new String[]{"Chaos", "CrashSite", "RaceTrack", "TestBoard"}; //add new maps as they're made
    private int currentMapIndex = 0;
    private String currentMap = maps[currentMapIndex];

    public void mapChangeRightClicked() {
        currentMap = maps[Math.floorMod(++currentMapIndex, maps.length)];
    }

    public void mapChangeLeftClicked() {
        currentMap = maps[Math.floorMod(--currentMapIndex, maps.length)];
    }

    public String getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(String map) {
        this.currentMap = map;
    }
}
