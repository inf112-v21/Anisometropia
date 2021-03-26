package screens;

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

import java.io.IOException;

public class OnNetSetupScreen extends AbstractScreen implements InputProcessor {
    SpriteBatch batch;
    OrthographicCamera camera;
    launcher.GameApplication gameApplication;
    Texture onNetSetupTexture;
    TextureRegion[][] onNetSetupRegionBy256, onNetSetupRegionBy128, onNetSetupRegionBy32, onNetSetupRegionBy64, multiPlayerButtons;
    TextureRegion start, back, startJumbled, backJumbled, hostBtnTexture, hostBtnOnTexture, hostBtnOffTexture;
    TextureRegion joinBtnTexture, joinBtnOnTexture, joinBtnOffTexture, sendBtnTexture, receiveBtnTexture;

    GameButton startBtn, backBtn, hostButton, joinButton, sendBtn, receiveBtn;
    BitmapFont font;

    int[] colElemPos = { 192, 260, 520, 580, 614, 758, 790, 840 };
    int[] rowElemPos = { 576, 512, 448, 384, 320, 256, 192, 128 };

    public static boolean isHost;
    private String status = "STATUS: waiting...";
    private String receiverString = "not started...";

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

        start = onNetSetupRegionBy128[6][0];
        startJumbled = onNetSetupRegionBy128[12][0];
        back = onNetSetupRegionBy128[6][1];
        backJumbled = onNetSetupRegionBy128[12][1];

        backBtn = new GameButton(426, 32, 256, 64, true, back);
        startBtn = new GameButton(756, 32, 256, 64, true, start);
        hostButton = new GameButton(1100,700, 85,85, true, hostBtnTexture);
        joinButton = new GameButton(1200, 700,85,85,true, joinBtnTexture);
        sendBtn = new GameButton(200,500, 85,85, false, sendBtnTexture);
        receiveBtn = new GameButton(200, 400,85,85,false, receiveBtnTexture);


        font = new BitmapFont();
        font.getData().setScale(1.8f);
        font.setColor(Color.BLACK);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();

        font.draw(batch, status, 1100, 820);
        font.draw(batch, receiverString, 600, 400);

        batch.draw(hostButton.getTexture(),hostButton.getX(),hostButton.getY(),hostButton.getWidth(),hostButton.getHeight());
        batch.draw(joinButton.getTexture(),joinButton.getX(),joinButton.getY(),joinButton.getWidth(),joinButton.getHeight());
        batch.draw(startBtn.getTexture(), startBtn.getX(), startBtn.getY(), startBtn.getWidth(), startBtn.getHeight());
        batch.draw(backBtn.getTexture(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());
        batch.draw(sendBtn.getTexture(), sendBtn.getX(), sendBtn.getY(), sendBtn.getWidth(), sendBtn.getHeight());
        batch.draw(receiveBtn.getTexture(), receiveBtn.getX(), receiveBtn.getY(), receiveBtn.getWidth(), receiveBtn.getHeight());

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

        if (Gdx.input.justTouched()) {
//            System.out.println("Mouse position: (" + Math.round(mousePosition.x) + ", " + Math.round(mousePosition.y) + ")");

            if(joinButton.isMouseOnButton(mousePosition)) joinButtonHasBeenClicked();
            if(hostButton.isMouseOnButton(mousePosition)) hostButtonHasBeenClicked();
            if(sendBtn.isMouseOnButton(mousePosition)) sendButtonHasBeenClicked();
            if(receiveBtn.isMouseOnButton(mousePosition)) receiveButtonHasBeenClicked();

            if(backBtn.isMouseOnButton(mousePosition)) backButtonClicked();
            if(startBtn.isMouseOnButton(mousePosition)) startButtonClicked();
        }
    }

    private void sendButtonHasBeenClicked() {

    }

    private void receiveButtonHasBeenClicked() {
    }

    private void hostButtonHasBeenClicked() {
        if (hostButton.isActive) {

            setHost(true);
            status = "STATUS: I am now HOST";

            sendBtn.setActive(true);
            receiveBtn.setActive(true);

            hostButton.setActive(false);
            hostButton.setTexture(hostBtnOnTexture);
            joinButton.setActive(false);
            joinButton.setTexture(joinBtnOffTexture);
        }
    }

    private void joinButtonHasBeenClicked() {
        if (joinButton.isActive) {

            setHost(false);
            status = "STATUS: I am now JOINING";

            joinButton.setActive(false);
            joinButton.setTexture(joinBtnOnTexture);
            hostButton.setActive(false);
            hostButton.setTexture(hostBtnOffTexture);
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

    public static void setHost(boolean i){
        isHost = i;
    }
    public static boolean isHost(){
        return isHost;
    }

    @Override
    public boolean keyTyped(char c) {

        return false;
    }

    private void startButtonClicked() {
        System.out.println("START button clicked!");
    }

    private void backButtonClicked() {
        gameApplication.gameScreenManager.setScreen(GameScreenManager.STATE.MENU);
    }

    @Override
    public void dispose() {
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
    public void show() {

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
