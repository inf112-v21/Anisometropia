package screens;

import actor.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import launcher.GameApplication;
import logic.GameLogic;
import logic.MultiPlayerLogic;
import logic.PlayerQueue;
import map.GraphicalGameMap;
import map.MapSelector;
import p2p.Multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class OnNetSetupScreen extends AbstractScreen implements InputProcessor {
    SpriteBatch batch;
    OrthographicCamera camera;
    launcher.GameApplication gameApplication;
    Texture onNetSetupTexture;
    TextureRegion[][] onNetSetupRegionBy256, onNetSetupRegionBy128, onNetSetupRegionBy32, onNetSetupRegionBy64, multiPlayerButtons;
    TextureRegion start, back, startInactive, startJumbled, backJumbled, hostBtnTexture, hostBtnOnTexture, hostBtnOffTexture;
    TextureRegion joinBtnTexture, joinBtnOnTexture, joinBtnOffTexture, sendBtnTexture, receiveBtnTexture;
    TextureRegion editLocalhostTexture, editLocalhostInactiveTexture, editPortTexture, editPortInactiveTexture;
    TextureRegion editPlayerNameTexture, editPlayerNameInactiveTexture;
    TextureRegion selectMap, mapChangeLeftTexture, mapChangeRightTexture ,mapChangeLeftHoveredTexture, mapChangeRightHoveredTexture;

    GameButton startBtn, backBtn, hostButton, joinButton, editLocalHostBtn, editPortBtn, editPlayerNameBtn;
    GameButton mapChangeLeft, mapChangeRight;
    BitmapFont font;

    int[] colElemPos = { 192, 260, 520, 580, 614, 758, 790, 840 };
    int[] rowElemPos = { 576, 512, 448, 384, 320, 256, 192, 128 };

    private GraphicalGameMap gameMap;
    private PlayerQueue playerQueue;
    private GameLogic gameLogic;
    public static boolean isHost;
    public static boolean isClient;

    private int editorIndex = -1;
    private final int numberOfInputEditors = 3;
    StringBuilder[] allStringBuilders = new StringBuilder[numberOfInputEditors]; // localhost + port + local player
    private MapSelector mapSelector = new MapSelector();
    public HashMap<Integer, String> playerNames = new HashMap<>();

    public static int playerID = 0;
    public static int numPlayers;
    public static boolean canStart;

    MultiPlayerLogic multiPlayerLogic;
    public Thread mpThread;

    public OnNetSetupScreen(GameApplication gameApplication) {
        super(gameApplication);
        this.gameApplication = gameApplication;
        batch = gameApplication.spriteBatch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        onNetSetupTexture = new Texture("menu_making.png");
        onNetSetupRegionBy256 = TextureRegion.split(onNetSetupTexture, 256, 32);
        onNetSetupRegionBy128 = TextureRegion.split(onNetSetupTexture, 128, 32);
        onNetSetupRegionBy32 = TextureRegion.split(onNetSetupTexture, 32, 32);
        onNetSetupRegionBy64 = TextureRegion.split(onNetSetupTexture, 64, 32);

        multiPlayerButtons = TextureRegion.split(new Texture("multiPlayerButtons.png"), 400,400);
        hostBtnTexture = multiPlayerButtons[0][0];
        hostBtnOffTexture = multiPlayerButtons[1][0];
        hostBtnOnTexture = multiPlayerButtons[2][0];
        joinBtnTexture = multiPlayerButtons[0][1];
        joinBtnOffTexture = multiPlayerButtons[1][1];
        joinBtnOnTexture = multiPlayerButtons[2][1];

        editLocalhostTexture = onNetSetupRegionBy256[13][0];
        editLocalhostInactiveTexture = onNetSetupRegionBy256[14][0];
        editPortTexture = onNetSetupRegionBy256[13][0];
        editPortInactiveTexture = onNetSetupRegionBy256[14][0];
        editPlayerNameTexture = onNetSetupRegionBy256[13][0];
        editPlayerNameInactiveTexture = onNetSetupRegionBy256[14][0];

        start = onNetSetupRegionBy128[6][0];
        startInactive = onNetSetupRegionBy128[21][0];
        startJumbled = onNetSetupRegionBy128[12][0];
        back = onNetSetupRegionBy128[6][1];
        backJumbled = onNetSetupRegionBy128[12][1];

        backBtn = new GameButton(426, 32, 256, 64, true, back);
        startBtn = new GameButton(756, 32, 256, 64, true, startInactive);
        hostButton = new GameButton(1100,700, 85,85, true, hostBtnTexture);
        joinButton = new GameButton(1200, 700,85,85,true, joinBtnTexture);
        editLocalHostBtn = new GameButton(1100, 640,256,32,false, editLocalhostInactiveTexture);
        editPortBtn = new GameButton(1100, 600,256,32,false, editPortInactiveTexture);
        editPlayerNameBtn = new GameButton(640, 500,256,32,false, editPlayerNameInactiveTexture);

        selectMap = onNetSetupRegionBy256[11][0];
        mapChangeLeftTexture = onNetSetupRegionBy32[10][0];
        mapChangeRightTexture = onNetSetupRegionBy32[10][1];
        mapChangeLeftHoveredTexture = onNetSetupRegionBy32[10][4];
        mapChangeRightHoveredTexture = onNetSetupRegionBy32[10][5];
        mapChangeLeft = new GameButton(colElemPos[1]+300, rowElemPos[1]+200, 32, 32, true, mapChangeLeftTexture);
        mapChangeRight = new GameButton(colElemPos[1]+600, rowElemPos[1]+200, 32, 32, true, mapChangeRightTexture);

        for (int i = 0; i < numberOfInputEditors; i++) {
            allStringBuilders[i] = new StringBuilder();
        }
        allStringBuilders[0] = new StringBuilder("localhost");
        allStringBuilders[1] = new StringBuilder("59999");
        allStringBuilders[2] = new StringBuilder("name");

        font = new BitmapFont();
        font.getData().setScale(1.8f);
        font.setColor(Color.BLACK);

        multiPlayerLogic = new MultiPlayerLogic();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();

        font.draw(batch, "IP: ", editLocalHostBtn.getX()-36, editLocalHostBtn.getY()+26);
        font.draw(batch, "PORT", editPortBtn.getX()-86, editPortBtn.getY()+26);
        font.draw(batch, "Your player name: ", editPlayerNameBtn.getX()-232, editPlayerNameBtn.getY()+26);
        font.draw(batch, "       Your PlayerID:   "+playerID, 400, 460);
        font.draw(batch, "Amount of players:   "+numPlayers, 400, 400);
        if(canStart && numPlayers != 0) {
            startBtn.setActive(true);
        }

        batch.draw(hostButton.getTexture(),hostButton.getX(),hostButton.getY(),hostButton.getWidth(),hostButton.getHeight());
        batch.draw(joinButton.getTexture(),joinButton.getX(),joinButton.getY(),joinButton.getWidth(),joinButton.getHeight());
        batch.draw(startBtn.getTexture(), startBtn.getX(), startBtn.getY(), startBtn.getWidth(), startBtn.getHeight());
        batch.draw(backBtn.getTexture(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());

        batch.draw(selectMap, colElemPos[1], rowElemPos[1]+200, selectMap.getRegionWidth(), selectMap.getRegionHeight());
        batch.draw(mapChangeLeft.getTexture(), mapChangeLeft.getX(), mapChangeLeft.getY(), mapChangeLeft.getWidth(), mapChangeLeft.getHeight());
        batch.draw(mapChangeRight.getTexture(), mapChangeRight.getX(),  mapChangeRight.getY(), mapChangeRight.getWidth(), mapChangeRight.getHeight());
        font.draw(batch, mapSelector.getCurrentMap(), mapChangeLeft.getX()+52, mapChangeLeft.getY()+28);

        setEditorIndex(editorIndex);

        batch.draw(editLocalHostBtn.getTexture(), editLocalHostBtn.getX(), editLocalHostBtn.getY(), editLocalHostBtn.getWidth(), editLocalHostBtn.getHeight());
        font.draw(batch, allStringBuilders[0].toString(),  editLocalHostBtn.getX()+8,  editLocalHostBtn.getY()+26);
        batch.draw(editPortBtn.getTexture(), editPortBtn.getX(), editPortBtn.getY(), editPortBtn.getWidth(), editPortBtn.getHeight());
        font.draw(batch, allStringBuilders[1].toString(),  editPortBtn.getX()+8,  editPortBtn.getY()+26);
        batch.draw(editPlayerNameBtn.getTexture(), editPlayerNameBtn.getX(), editPlayerNameBtn.getY(), editPlayerNameBtn.getWidth(), editPlayerNameBtn.getHeight());
        font.draw(batch, allStringBuilders[2].toString(),  editPlayerNameBtn.getX()+8,  editPlayerNameBtn.getY()+26);

        batch.end();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameApplication.gameScreenManager.setScreen(GameScreenManager.STATE.MENU);
        }
        Vector3 mousePosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        ifHoveredMakeBackButtonBlueAndJumbled(mousePosition);
        ifHoveredMakeStartButtonBlueAndJumbled(mousePosition);
        if (!isHost && !isClient) {
            ifHoveredMakeMapChangeLeftBlue(mousePosition);
            ifHoveredMakeMapChangeRightBlue(mousePosition);
        }

        if (Gdx.input.justTouched()) {
            if(joinButton.isMouseOnButton(mousePosition)) joinButtonHasBeenClicked();
            if(hostButton.isMouseOnButton(mousePosition)) hostButtonHasBeenClicked();

            if (!isHost && !isClient) {
                if (mapChangeLeft.isMouseOnButton(mousePosition)) mapSelector.mapChangeLeftClicked();
                if (mapChangeRight.isMouseOnButton(mousePosition)) mapSelector.mapChangeRightClicked();
            }

            editorIndex = indexOfInputEditorUnderMousePosition(mousePosition);

            if(backBtn.isMouseOnButton(mousePosition)) backButtonClicked();
            if(startBtn.isMouseOnButton(mousePosition)) startButtonClicked();
        }
    }

    private int indexOfInputEditorUnderMousePosition(Vector3 mousePosition) {
        if (editLocalHostBtn.isMouseOnButton(mousePosition)) {
            return 0;
        } else if (editPortBtn.isMouseOnButton(mousePosition)) {
            return 1;
        } else if (editPlayerNameBtn.isMouseOnButton(mousePosition)) {
            return 2;
        }
        return -1;
    }

    /**
     * Sets up a new connection as host.
     */
    private void hostButtonHasBeenClicked() {
        if (hostButton.isActive) {
            gameMap = new GraphicalGameMap(mapSelector.getCurrentMap());
            playerQueue = new PlayerQueue();
            gameLogic = new GameLogic(gameMap, playerQueue, multiPlayerLogic);
            multiPlayerLogic.setGameLogic(gameLogic);
            multiPlayerLogic.mp = new Multiplayer(Boolean.TRUE, this);
            multiPlayerLogic.initializeID();
            Thread mpThread = new Thread(multiPlayerLogic.mp);
            mpThread.start();

            setHost(true);

            mapChangeLeft.setTexture(onNetSetupRegionBy32[21][4]);
            mapChangeRight.setTexture(onNetSetupRegionBy32[21][5]);
            startBtn.setActive(true);
            startBtn.setTexture(start);
            hostButton.setActive(false);
            hostButton.setTexture(hostBtnOnTexture);
            joinButton.setActive(false);
            joinButton.setTexture(joinBtnOffTexture);
        }
    }

    /**
     * Connects to host. When connection is formed, waits for host to start the game.
     * TODO: Avoid crashing when no host is available.
     */
    private void joinButtonHasBeenClicked() {
        if (joinButton.isActive) {
            multiPlayerLogic.mp = new Multiplayer(Boolean.FALSE, this);
            mpThread = new Thread(multiPlayerLogic.mp);
            mpThread.start();

            isClient = true;

            mapChangeLeft.setTexture(onNetSetupRegionBy32[21][4]);
            mapChangeRight.setTexture(onNetSetupRegionBy32[21][5]);
            joinButton.setActive(false);
            joinButton.setTexture(joinBtnOnTexture);
            hostButton.setActive(false);
            hostButton.setTexture(hostBtnOffTexture);

            multiPlayerLogic.mp.setToSend("INITIAL_ID_REQUEST");
        }
    }

    /**
     * When host clicks start button, clients are signaled to do the same.
     * Player IDs are requested and sent. The game is initiated.
     * TODO: Support three or more players.
     */
    public void startButtonClicked() {
        if (isHost) {
            multiPlayerLogic.mp.setToSend("START");
            startGame();
        } else if (multiPlayerLogic.isConnected()){
            if(canStart && numPlayers != 0) startGame();
        }
    }

    public void startGame() {
        if (isClient) {
            gameMap = new GraphicalGameMap(mapSelector.getCurrentMap());
            playerQueue = new PlayerQueue();
            gameLogic = new GameLogic(gameMap, playerQueue, multiPlayerLogic);
            multiPlayerLogic.setGameLogic(gameLogic);
        }
        int spawnIncrementer = 0;
        // Store your designated player instance as local to you, all other players non-local.
        for (int i = 0; i < numPlayers; i++) {
            if (playerID == i) {
                playerQueue.add(new Player((int) gameMap.getSpawnPoint(spawnIncrementer).getX(),
                        (int) gameMap.getSpawnPoint(spawnIncrementer).getY(), allStringBuilders[2].toString(), gameMap, true, i));
            }
            else {
                playerQueue.add(new Player((int) gameMap.getSpawnPoint(spawnIncrementer).getX(),
                        (int) gameMap.getSpawnPoint(spawnIncrementer).getY(), playerNames.get(i), gameMap, false, i));
            }
            spawnIncrementer++;
        }

        for (Player player : playerQueue.getPlayerQueue() ) {
            player.setPlayerQueue(playerQueue);
        }

        gameLogic.setPlayerQueue(playerQueue);
        gameLogic.dealProgramCards();
        gameApplication.gameScreenManager.initPlayScreen(gameMap, gameLogic);
        gameApplication.gameScreenManager.setScreen(GameScreenManager.STATE.PLAY);
    }

    private void ifHoveredMakeMapChangeLeftBlue(Vector3 mousePosition) {
        if (mapChangeLeft.isMouseOnButton(mousePosition)) {
            mapChangeLeft.setTexture(mapChangeLeftHoveredTexture);
        }else{
            mapChangeLeft.setTexture(mapChangeLeftTexture);
        }
    }

    private void ifHoveredMakeMapChangeRightBlue(Vector3 mousePosition) {
        if (mapChangeRight.isMouseOnButton(mousePosition)) {
            mapChangeRight.setTexture(mapChangeRightHoveredTexture);
        }else{
            mapChangeRight.setTexture(mapChangeRightTexture);
        }
    }

    private void ifHoveredMakeStartButtonBlueAndJumbled(Vector3 mousePosition) {
        if (canStart || isHost) {
            if (startBtn.isMouseOnButton(mousePosition)) {
                startBtn.setTexture(startJumbled);
            }else{
                startBtn.setTexture(start);
            }
        }
    }

    private void ifHoveredMakeBackButtonBlueAndJumbled(Vector3 mousePosition) {
        if (backBtn.isMouseOnButton(mousePosition)) {
            backBtn.setTexture(backJumbled);
        }else{
            backBtn.setTexture(back);
        }
    }

    public MultiPlayerLogic getMultiplayerLogic() {
        return multiPlayerLogic;
    }

    public static void setHost(boolean i){
        isHost = i;
    }

    public ArrayList<Boolean> getPlayersReady() {
        return multiPlayerLogic.playersReady;
    }

    public void setPlayersReady(int index, boolean bool) {
        multiPlayerLogic.playersReady.set(index, bool);
    }

    public void addUnreadyPlayerToPlayerReadyList() {
        multiPlayerLogic.playersReady.add(false);
    }

    public String getIPAddress() {
        return allStringBuilders[0].toString();
    }

    public int getPort() {
        return Integer.parseInt(allStringBuilders[1].toString());
    }

    private void setEditorIndex(int index){
        if (index == 0) editLocalHostBtn.setTexture(editLocalhostTexture);
        else editLocalHostBtn.setTexture(editLocalhostInactiveTexture);
        if (index == 1) editPortBtn.setTexture(editPortTexture);
        else editPortBtn.setTexture(editPortInactiveTexture);
        if (index == 2) editPlayerNameBtn.setTexture(editPlayerNameTexture);
        else editPlayerNameBtn.setTexture(editPlayerNameInactiveTexture);
    }

    @Override
    public boolean keyTyped(char c) {
        if (!multiPlayerLogic.isConnected()) {
            if (editorIndex != -1) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
                    if (allStringBuilders[editorIndex].length() > 0)
                        allStringBuilders[editorIndex].replace(
                                allStringBuilders[editorIndex].length() - 1, allStringBuilders[editorIndex].length(),
                                "");
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                    editorIndex = -1;
                } else if (allStringBuilders[editorIndex].length() < 12) {
                    allStringBuilders[editorIndex].append(c);
                }
            }
        }
        return false;
    }

    private void backButtonClicked() {
        gameApplication.gameScreenManager.setScreen(GameScreenManager.STATE.MENU);
    }

    public String getCurrentMap() {
        return mapSelector.getCurrentMap();
    }

    public void setCurrentMap(String map) {
        mapSelector.setCurrentMap(map);
    }

    public String getPlayerName(){
        return allStringBuilders[2].toString();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        if (multiPlayerLogic.isConnected()) {
            multiPlayerLogic.mp.connected = false;
            try {
                multiPlayerLogic.mp.disconnect();
            } catch (IOException ignored) {
            }
        }
        font.dispose();
        onNetSetupTexture.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }

}
