package screens;

import actor.AIPlayer;
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
import logic.PlayerQueue;
import map.GraphicalGameMap;

public class LocalSetupScreen extends AbstractScreen implements InputProcessor {

    SpriteBatch batch;
    OrthographicCamera camera;
    launcher.GameApplication gameApplication;

    Texture localSetupTexture;
    TextureRegion[][] localSetupRegionBy256, localSetupRegionBy128, localSetupRegionBy32, localSetupRegionBy64;
    TextureRegion selectMap, add, addHovered, remove, removeHovered, start, back, startJumbled, backJumbled;
    TextureRegion plSelected, plSelectedHovered, aiSelected, aiSelectedHovered, scrollLeft, scrollRight, moveUp, moveDown;
    TextureRegion nameEditSquareActive, nameEditSquareInactive;

    GameButton startBtn, backBtn, addBtn;
    GameButton[] removeButtons;
    GameButton[] scrollLeftButtons;
    GameButton[] scrollRightButtons;
    GameButton[] moveUpButtons;
    GameButton[] moveDownButtons;
    GameButton[] editNameButtons;
    GameButton[] plOrAiButtons;

    TextureRegion[] numbers;
    TextureRegion[] characters;

    BitmapFont font;

    int editNameIndexActive = -1;
    int maxPlayers = 4;
    int playersAdded = 1;
    boolean[] isAi = new boolean[maxPlayers];
    StringBuilder[] allStringBuilders = new StringBuilder[maxPlayers];

    int[] colElemPos = { 192, 260, 520, 580, 614, 758, 790, 840 };
    int[] rowElemPos = { 576, 512, 448, 384, 320, 256, 192, 128 };

    public LocalSetupScreen(GameApplication gameApplication) {
        super(gameApplication);
        this.gameApplication = gameApplication;
        batch = gameApplication.spriteBatch;

        camera = new OrthographicCamera();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        localSetupTexture = new Texture("menu_making.png");
        localSetupRegionBy256 = TextureRegion.split(localSetupTexture, 256, 32);
        localSetupRegionBy128 = TextureRegion.split(localSetupTexture, 128, 32);
        localSetupRegionBy32 = TextureRegion.split(localSetupTexture, 32, 32);
        localSetupRegionBy64 = TextureRegion.split(localSetupTexture, 64, 32);
        selectMap = localSetupRegionBy256[11][0];
        add = localSetupRegionBy128[5][1];
        addHovered =  localSetupRegionBy128[15][1];
        remove = localSetupRegionBy128[5][0];
        removeHovered =  localSetupRegionBy128[15][0];
        start = localSetupRegionBy128[6][0];
        startJumbled = localSetupRegionBy128[12][0];
        back = localSetupRegionBy128[6][1];
        backJumbled = localSetupRegionBy128[12][1];
        nameEditSquareActive = localSetupRegionBy256[13][0];
        nameEditSquareInactive = localSetupRegionBy256[14][0];
        scrollLeft = localSetupRegionBy32[10][0];
        scrollRight = localSetupRegionBy32[10][1];
        moveUp = localSetupRegionBy32[10][2];
        moveDown = localSetupRegionBy32[10][3];
        plSelected = localSetupRegionBy128[8][0];
        aiSelected = localSetupRegionBy128[8][1];
        plSelectedHovered = localSetupRegionBy128[7][0];
        aiSelectedHovered = localSetupRegionBy128[7][1];

        removeButtons = new GameButton[maxPlayers];
        scrollLeftButtons = new GameButton[maxPlayers];
        scrollRightButtons = new GameButton[maxPlayers];
        moveUpButtons = new GameButton[maxPlayers];
        moveDownButtons = new GameButton[maxPlayers];
        editNameButtons = new GameButton[maxPlayers];
        plOrAiButtons = new GameButton[maxPlayers];

        numbers = new TextureRegion[maxPlayers];
        for (int i = 0; i < maxPlayers; i++) {
            numbers[i] = localSetupRegionBy32[9][i];
        }

        characters = new TextureRegion[4]; // TODO: should be "maxPlayers" instead of 4, change when all 8 characters are done!
        for (int i = 0; i < maxPlayers; i++) {
            characters[i] = localSetupRegionBy32[16][4+i];
        }

        for (int i = 0; i < maxPlayers; i++) {
            editNameButtons[i] = new GameButton(colElemPos[1], rowElemPos[i], 256,32, false, nameEditSquareInactive);
            scrollLeftButtons[i] = new GameButton(colElemPos[2], rowElemPos[i], 32, 32, true, scrollLeft);
            scrollRightButtons[i] = new GameButton(colElemPos[3], rowElemPos[i], 32, 32, true, scrollRight);
            plOrAiButtons[i] = new GameButton(colElemPos[4], rowElemPos[i], 128, 32, true, plSelected);
            moveUpButtons[i] = new GameButton(colElemPos[5], rowElemPos[i], 32, 32, true, moveUp);
            moveDownButtons[i] = new GameButton(colElemPos[6], rowElemPos[i], 32, 32, true, moveDown);
            removeButtons[i] = new GameButton(colElemPos[7], rowElemPos[i], 128, 32, true, remove);
        }

        addBtn = new GameButton(colElemPos[1], rowElemPos[1], 128, 32, true, add);

        backBtn = new GameButton(426, 32, 256, 64, true, back);
        startBtn = new GameButton(756, 32, 256, 64, true, start);

        allStringBuilders[0] = new StringBuilder("name");
        for (int i = 1; i < maxPlayers; i++) {
            allStringBuilders[i] = new StringBuilder();
        }

        font = new BitmapFont();
        font.getData().setScale(1.8f);
        font.setColor(Color.BLACK);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(selectMap, colElemPos[1], rowElemPos[1]+200, selectMap.getRegionWidth(), selectMap.getRegionHeight());

        for (int i = 0; i < playersAdded; i++) {
            showAddedPlayerRows(batch, i);
        }

        if (addBtn.isActive) {
            batch.draw(addBtn.getTexture(), addBtn.getX(), addBtn.getY(), addBtn.getWidth(), addBtn.getHeight());
        }

        batch.draw(startBtn.getTexture(), startBtn.getX(), startBtn.getY(), startBtn.getWidth(), startBtn.getHeight());
        batch.draw(backBtn.getTexture(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());

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
        ifHoveredMakeAddButtonBlue(mousePosition);
        ifHoveredMakeAiSelectorBlue(mousePosition);
        ifHoveredMakeRemoveButtonBlue(mousePosition);


        if (Gdx.input.justTouched()) {
//            System.out.println("Mouse position: (" + Math.round(mousePosition.x) + ", " + Math.round(mousePosition.y) + ")");
            editNameIndexActive = indexOfCurrentNameEditUnderMousePosition(editNameButtons, mousePosition, playersAdded);

            if(backBtn.isMouseOnButton(mousePosition)) backButtonClicked();
            if(startBtn.isMouseOnButton(mousePosition)) startButtonClicked();

            ifClickedSwitchPlayerOrAiStatus(mousePosition);
            ifClickedRemoveAddedPlayer(mousePosition);
            ifClickedAddPlayer(mousePosition);
        }
    }

    private int indexOfCurrentNameEditUnderMousePosition(GameButton[] gameButton, Vector3 mousePosition, int totalToCheck) {
        for (int i = 0; i < totalToCheck; i++) {
            if (gameButton[i].isMouseOnButton(mousePosition)) {
                return i;
            }
        }
        return -1;
    }

    private void ifClickedAddPlayer(Vector3 mousePosition) {
        if(addBtn.isMouseOnButton(mousePosition)) {
            if (playersAdded < maxPlayers) {
                playersAdded++;
                allStringBuilders[playersAdded -1].delete(0, allStringBuilders[playersAdded -1].length());
                addBtn.setY(rowElemPos[0] - (playersAdded * 64));
                if(playersAdded == maxPlayers) addBtn.setActive(false);
            }
        }
    }

    private void ifClickedRemoveAddedPlayer(Vector3 mousePosition) {
        for (int i = 0; i < playersAdded; i++) {
            if(removeButtons[i].isMouseOnButton(mousePosition)) {
                for (int j = i; j < playersAdded; j++) {
                    if(j != playersAdded - 1) {
                        allStringBuilders[j].delete(0, allStringBuilders[j].length());
                        allStringBuilders[j].append(allStringBuilders[j+1]);
                        isAi[j] = isAi[j+1];
                    }
                }
                playersAdded--;
                addBtn.setY(rowElemPos[0] - (playersAdded * 64));
                addBtn.setActive(true);
                break;
            }
        }
    }

    private void ifClickedSwitchPlayerOrAiStatus(Vector3 mousePosition) {
        for (int i = 0; i < playersAdded; i++) {
            if(plOrAiButtons[i].isMouseOnButton(mousePosition)) {
                if (isAi[i]) {
                    isAi[i] = false;
                    plOrAiButtons[i].setTexture(plSelected);
                } else {
                    isAi[i] = true;
                    plOrAiButtons[i].setTexture(aiSelected);
                }
                break;
            }
        }
    }

    private void ifHoveredMakeRemoveButtonBlue(Vector3 mousePosition) {
        for (int i = 1; i < playersAdded; i++) {
            if(removeButtons[i].isMouseOnButton(mousePosition)) {
                removeButtons[i].setTexture(removeHovered);
            }else{
                removeButtons[i].setTexture(remove);
            }
        }
    }

    private void ifHoveredMakeAiSelectorBlue(Vector3 mousePosition) {
        for (int i = 0; i < playersAdded; i++) {
            if(plOrAiButtons[i].isMouseOnButton(mousePosition)) {
                if(isAi[i]){
                    plOrAiButtons[i].setTexture(aiSelectedHovered);
                } else {
                    plOrAiButtons[i].setTexture(plSelectedHovered);
                }
            }else{
                if(isAi[i]){
                    isAi[i] = true;
                    plOrAiButtons[i].setTexture(aiSelected);
                } else {
                    isAi[i] = false;
                    plOrAiButtons[i].setTexture(plSelected);
                }
            }
        }
    }

    private void ifHoveredMakeAddButtonBlue(Vector3 mousePosition) {
        if (addBtn.isMouseOnButton(mousePosition)) {
            addBtn.setTexture(addHovered);
        }else{
            addBtn.setTexture(add);
        }
    }

    private void ifHoveredMakeStartButtonBlueAndJumbled(Vector3 mousePosition) {
        if (startBtn.isMouseOnButton(mousePosition)) {
            startBtn.setTexture(startJumbled);
        }else{
            startBtn.setTexture(start);
        }
    }

    private void ifHoveredMakeBackButtonBlueAndJumbled(Vector3 mousePosition) {
        if (backBtn.isMouseOnButton(mousePosition)) {
            backBtn.setTexture(backJumbled);
        }else{
            backBtn.setTexture(back);
        }
    }

    public void showAddedPlayerRows(SpriteBatch batch, int index) {
        batch.draw(numbers[index], colElemPos[0], rowElemPos[index], numbers[index].getRegionWidth(), numbers[index].getRegionHeight());
        batch.draw(characters[index], scrollLeftButtons[index].getX()+scrollLeftButtons[index].getWidth(), scrollLeftButtons[index].getY(), characters[index].getRegionHeight(), characters[index].getRegionHeight());
        batch.draw(scrollLeftButtons[index].getTexture(), scrollLeftButtons[index].getX(), scrollLeftButtons[index].getY(), scrollLeftButtons[index].getWidth(), scrollLeftButtons[index].getHeight());
        batch.draw(scrollRightButtons[index].getTexture(), scrollRightButtons[index].getX(), scrollRightButtons[index].getY(), scrollRightButtons[index].getWidth(), scrollRightButtons[index].getHeight());
        batch.draw(plOrAiButtons[index].getTexture(),scrollRightButtons[index].getX() + scrollRightButtons[index].getWidth()+2, scrollRightButtons[index].getY(), plSelected.getRegionWidth(), plSelected.getRegionHeight());
        batch.draw(moveUpButtons[index].getTexture(), moveUpButtons[index].getX(), moveUpButtons[index].getY(), moveUpButtons[index].getWidth(), moveUpButtons[index].getHeight());
        batch.draw(moveDownButtons[index].getTexture(), moveDownButtons[index].getX(), moveDownButtons[index].getY(), moveDownButtons[index].getWidth(), moveDownButtons[index].getHeight());

        if(index != 0) batch.draw(removeButtons[index].getTexture(), removeButtons[index].getX(), removeButtons[index].getY(), removeButtons[index].getWidth(), removeButtons[index].getHeight());

        if (editNameIndexActive == index) editNameButtons[index].setTexture(nameEditSquareActive);
        else editNameButtons[index].setTexture(nameEditSquareInactive);
        batch.draw(editNameButtons[index].getTexture(), editNameButtons[index].getX(), editNameButtons[index].getY(), editNameButtons[index].getWidth(), editNameButtons[index].getHeight());
        font.draw(batch, allStringBuilders[index].toString(), editNameButtons[index].getX()+8, editNameButtons[index].getY()+26);
    }

    @Override
    public boolean keyTyped(char c) {
        if (editNameIndexActive != -1) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
                if (allStringBuilders[editNameIndexActive].length() > 0)
                    allStringBuilders[editNameIndexActive].replace(allStringBuilders[editNameIndexActive].length() - 1, allStringBuilders[editNameIndexActive].length(), "");
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                editNameIndexActive = -1;
            } else if (allStringBuilders[editNameIndexActive].length() < 12){
                allStringBuilders[editNameIndexActive].append(c);
            }
        }
        return false;
    }

    private void startButtonClicked() {
        GraphicalGameMap gameMap = new GraphicalGameMap();
        PlayerQueue playerQueue = new PlayerQueue();
        int spawnIncrementer = 0;
        for (int i = 0; i < playersAdded; i++) {
            if(isAi[i]) playerQueue.add(new AIPlayer((int) gameMap.getSpawnPoint(spawnIncrementer).getX(), (int) gameMap.getSpawnPoint(spawnIncrementer).getY(), allStringBuilders[i].toString(), gameMap, i));
            else playerQueue.add(new Player((int) gameMap.getSpawnPoint(spawnIncrementer).getX(), (int) gameMap.getSpawnPoint(spawnIncrementer).getY(), allStringBuilders[i].toString(), gameMap, i));
            spawnIncrementer++;

        }
        gameApplication.gameScreenManager.initPlayScreen(gameMap, playerQueue);
        gameApplication.gameScreenManager.setScreen(GameScreenManager.STATE.PLAY);
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
        font.dispose();
        localSetupTexture.dispose();
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