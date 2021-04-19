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
import p2p.Multiplayer;

import java.io.IOException;
import java.util.ArrayList;

public class OnNetSetupScreen extends AbstractScreen implements InputProcessor {
    SpriteBatch batch;
    OrthographicCamera camera;
    launcher.GameApplication gameApplication;
    Texture onNetSetupTexture;
    TextureRegion[][] onNetSetupRegionBy256, onNetSetupRegionBy128, onNetSetupRegionBy32, onNetSetupRegionBy64, multiPlayerButtons;
    TextureRegion start, back, startInactive, startJumbled, backJumbled, hostBtnTexture, hostBtnOnTexture, hostBtnOffTexture;
    TextureRegion joinBtnTexture, joinBtnOnTexture, joinBtnOffTexture, sendBtnTexture, receiveBtnTexture;
    TextureRegion editLocalhostTexture, editLocalhostInactiveTexture, editPortTexture, editPortInactiveTexture;

    GameButton startBtn, backBtn, hostButton, joinButton, sendBtn, receiveBtn, editLocalHostBtn, editPortBtn;
    BitmapFont font;

    int[] colElemPos = { 192, 260, 520, 580, 614, 758, 790, 840 };
    int[] rowElemPos = { 576, 512, 448, 384, 320, 256, 192, 128 };

    private GraphicalGameMap gameMap;
    private PlayerQueue playerQueue;
    private GameLogic gameLogic;
    public static boolean isHost;
    public static boolean connected; // TODO: will probably be replaced by another boolean/function

    private int editorIndex = -1;
    private final int numberOfInputEditors = 3;
    StringBuilder[] allStringBuilders = new StringBuilder[numberOfInputEditors]; // localhost + port + local player

    private String status = "STATUS: waiting...";
    private String ipLabel = "IP:";
    private String portLabel = "PORT:";
    private String gameReadyStatus = "GAME IS NOT READY TO START";
    private String getUpdateMessage = "Click RECEIVE-button to get update from server";

    public static int playerID = 0;
    public static int numPlayers;
    public static boolean canStart;

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
        sendBtnTexture = multiPlayerButtons[3][0];
        receiveBtnTexture = multiPlayerButtons[3][1];

        editLocalhostTexture = onNetSetupRegionBy256[13][0];
        editLocalhostInactiveTexture = onNetSetupRegionBy256[14][0];
        editPortTexture = onNetSetupRegionBy256[13][0];
        editPortInactiveTexture = onNetSetupRegionBy256[14][0];

        start = onNetSetupRegionBy128[6][0];
        startInactive = onNetSetupRegionBy128[21][0];
        startJumbled = onNetSetupRegionBy128[12][0];
        back = onNetSetupRegionBy128[6][1];
        backJumbled = onNetSetupRegionBy128[12][1];

        backBtn = new GameButton(426, 32, 256, 64, true, back);
        startBtn = new GameButton(756, 32, 256, 64, true, startInactive);
        hostButton = new GameButton(1100,700, 85,85, true, hostBtnTexture);
        joinButton = new GameButton(1200, 700,85,85,true, joinBtnTexture);
        sendBtn = new GameButton(200,500, 85,85, false, sendBtnTexture);
        receiveBtn = new GameButton(200, 600,85,85,false, receiveBtnTexture);
        editLocalHostBtn = new GameButton(1100, 640,256,32,false, receiveBtnTexture);
        editPortBtn = new GameButton(1100, 600,256,32,false, receiveBtnTexture);

        for (int i = 0; i < numberOfInputEditors; i++) {
            allStringBuilders[i] = new StringBuilder();
        }
        allStringBuilders[0] = new StringBuilder("localhost");
        allStringBuilders[1] = new StringBuilder("59999");

        font = new BitmapFont();
        font.getData().setScale(1.8f);
        font.setColor(Color.BLACK);

        gameMap = new GraphicalGameMap();
        playerQueue = new PlayerQueue();
        gameLogic = new GameLogic(gameMap, playerQueue);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();

        font.draw(batch, status, 1100, 820);
        font.draw(batch, ipLabel, editLocalHostBtn.getX()-36, editLocalHostBtn.getY()+26);
        font.draw(batch, portLabel, editPortBtn.getX()-86, editPortBtn.getY()+26);
        font.draw(batch, "       Your PlayerID:   "+playerID, 400, 460);
        font.draw(batch, "Amount of players:   "+numPlayers, 400, 400);
        if(canStart && numPlayers != 0) {
            gameReadyStatus = "READY TO START!";
            startBtn.setActive(true);
        }
        if(!isHost) font.draw(batch, gameReadyStatus, 540, 180);
        if(gameLogic.multiPlayerLogic.isConnected()) font.draw(batch, getUpdateMessage, receiveBtn.getX()+120, receiveBtn.getY()+54);

        batch.draw(hostButton.getTexture(),hostButton.getX(),hostButton.getY(),hostButton.getWidth(),hostButton.getHeight());
        batch.draw(joinButton.getTexture(),joinButton.getX(),joinButton.getY(),joinButton.getWidth(),joinButton.getHeight());
        batch.draw(startBtn.getTexture(), startBtn.getX(), startBtn.getY(), startBtn.getWidth(), startBtn.getHeight());
        batch.draw(backBtn.getTexture(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());

        if (editorIndex == 0) editLocalHostBtn.setTexture(editLocalhostTexture);
        else editLocalHostBtn.setTexture(editLocalhostInactiveTexture);

        if (editorIndex == 1) editPortBtn.setTexture(editPortTexture);
        else editPortBtn.setTexture(editPortInactiveTexture);

        batch.draw(editLocalHostBtn.getTexture(), editLocalHostBtn.getX(), editLocalHostBtn.getY(), editLocalHostBtn.getWidth(), editLocalHostBtn.getHeight());
        font.draw(batch, allStringBuilders[0].toString(),  editLocalHostBtn.getX()+8,  editLocalHostBtn.getY()+26);
        batch.draw(editPortBtn.getTexture(), editPortBtn.getX(), editPortBtn.getY(), editPortBtn.getWidth(), editPortBtn.getHeight());
        font.draw(batch, allStringBuilders[1].toString(),  editPortBtn.getX()+8,  editPortBtn.getY()+26);

        batch.end();
    }

    @Override
    public void update(float delta) throws IOException {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameApplication.gameScreenManager.setScreen(GameScreenManager.STATE.MENU);
        }
        Vector3 mousePosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        ifHoveredMakeBackButtonBlueAndJumbled(mousePosition);
        ifHoveredMakeStartButtonBlueAndJumbled(mousePosition);

        if (Gdx.input.justTouched()) {
            if(joinButton.isMouseOnButton(mousePosition)) joinButtonHasBeenClicked();
            if(hostButton.isMouseOnButton(mousePosition)) hostButtonHasBeenClicked();
            if(sendBtn.isMouseOnButton(mousePosition)) sendButtonHasBeenClicked();
            if(receiveBtn.isMouseOnButton(mousePosition)) receiveButtonHasBeenClicked();

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
        }
        return -1;
    }

    private void sendButtonHasBeenClicked() {
    }

    private void receiveButtonHasBeenClicked() {
        gameLogic.multiPlayerLogic.mp.setToSend("AMOUNT_PLAYERS_REQUEST");
    }

    /**
     * Sets up a new connection as host.
     */
    private void hostButtonHasBeenClicked() throws IOException {
        if (hostButton.isActive) {
            gameLogic.multiPlayerLogic.mp = new Multiplayer(Boolean.TRUE, this);
            gameLogic.multiPlayerLogic.initializeID();
            Thread mpThread = new Thread(gameLogic.multiPlayerLogic.mp);
            mpThread.start();

            setHost(true);
            status = "STATUS: I am now HOST";

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
    private void joinButtonHasBeenClicked() throws IOException {
        if (joinButton.isActive) {
            gameLogic.multiPlayerLogic.mp = new Multiplayer(Boolean.FALSE, this);
            mpThread = new Thread(gameLogic.multiPlayerLogic.mp);
            mpThread.start();

            setHost(false);
            status = "STATUS: I am now JOINING";

            joinButton.setActive(false);
            joinButton.setTexture(joinBtnOnTexture);
            hostButton.setActive(false);
            hostButton.setTexture(hostBtnOffTexture);

            gameLogic.multiPlayerLogic.mp.setToSend("ID_REQUEST");
        }
    }

    /**
     * When host clicks start button, clients are signaled to do the same.
     * Player IDs are requested and sent. The game is initiated.
     * TODO: Only activate button for host.
     * TODO: Support three or more players.
     */
    public void startButtonClicked() throws IOException {
        if (isHost) {
            int playerID = 0;
            int numberOfPlayers;

            gameLogic.multiPlayerLogic.mp.setToSend("START");
            startGame();
        } else {
            gameLogic.multiPlayerLogic.mp.setToSend("AMOUNT_PLAYERS_REQUEST");
            System.out.println("MY PLAYER_ID: "+ playerID); // TODO: make a setter for this, and print out (now there is delay)
            System.out.println("AMOUNT OF PLAYERS: "+ numPlayers); // TODO: make setter for this, w/printout (currently delay)
            if(canStart && numPlayers != 0) startGame();
        }
    }

    public void startGame() {
        int spawnIncrementer = 0;
        // Store your designated player instance as local to you, all other players non-local.
        for (int i = 0; i < numPlayers; i++) {
            if (playerID == i) {
                playerQueue.add(new Player((int) gameMap.getSpawnPoint(spawnIncrementer).getX(),
                        (int) gameMap.getSpawnPoint(spawnIncrementer).getY(), allStringBuilders[i].toString(), gameMap, true, i));
            }
            else {
                playerQueue.add(new Player((int) gameMap.getSpawnPoint(spawnIncrementer).getX(),
                        (int) gameMap.getSpawnPoint(spawnIncrementer).getY(), allStringBuilders[i].toString(), gameMap, false, i));
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
        return gameLogic.multiPlayerLogic;
    }

    public static void setHost(boolean i){
        isHost = i;
    }

    public ArrayList<Boolean> getPlayersReady() {
        return getMultiplayerLogic().playersReady;
    }

    public void setPlayersReady(int index, boolean bool) {
        getMultiplayerLogic().playersReady.set(index, bool);
    }

    public void addToPlayersReady() {
        getMultiplayerLogic().playersReady.add(false);
    }

    public String getIPAddress() {
        return allStringBuilders[0].toString();
    }

    public int getPort() {
        return Integer.parseInt(allStringBuilders[1].toString());
    }

    @Override
    public boolean keyTyped(char c) {
        if (!connected) {
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
        } else {
            // TODO: what happens after connected = true (player selection setup)
        }
        return false;
    }

    private void backButtonClicked() {
        gameApplication.gameScreenManager.setScreen(GameScreenManager.STATE.MENU);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        gameLogic.multiPlayerLogic.mp.connected = false;
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
