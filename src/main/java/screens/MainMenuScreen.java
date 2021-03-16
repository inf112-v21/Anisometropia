package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import launcher.GameApplication;

public class MainMenuScreen extends AbstractScreen implements InputProcessor {

    GameApplication gameApplication;
    OrthographicCamera camera;
    Music menuMusic;
    boolean musicPlaying = true;

    float elapsedTime;
    Texture menuBackground;
    Texture mainMenuTexture;
    TextureRegion[][] mainMenuRegionBy256, mainMenuRegionBy128, mainMenuRegionBy64, mainMenuRegionBy32, menuBackGroundRegionBy1440;
    TextureRegion playOnNet, playOnNetJumbled, playLocal, playLocalJumbled, quit, quitJumbled, speakerOn, speakerOff;
    Texture menuBackgroundSpriteSheet;
    Animation<TextureRegion> animRightArrow, animLeftArrow, animBackground;
    int arrowRightPadX = -140;
    int arrowLeftPadX = 16;
    TextureRegion[] framesRightArrow, framesLeftArrow, framesMenuBackground;

    GameButton speaker, playLocalBtn, playOnNetBtn, quitBtn;

    public MainMenuScreen(GameApplication gameApplication) {
        super(gameApplication);
        this.gameApplication = gameApplication;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        menuBackground = new Texture(Gdx.files.internal("menu_mock-up.png"));
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("mainMenu.mp3"));
        menuMusic.setVolume(0.2f);
        menuMusic.setLooping(true);

        mainMenuTexture = new Texture("menu_making.png");
        mainMenuRegionBy256 = TextureRegion.split(mainMenuTexture, 256, 32);
        mainMenuRegionBy128 = TextureRegion.split(mainMenuTexture, 128, 32);
        mainMenuRegionBy64 = TextureRegion.split(mainMenuTexture, 64, 32);
        mainMenuRegionBy32 = TextureRegion.split(mainMenuTexture, 32, 32);

        menuBackgroundSpriteSheet = new Texture(Gdx.files.internal("menu_mock-up3-Sheet.png"));
        menuBackGroundRegionBy1440 = TextureRegion.split(menuBackgroundSpriteSheet, 1440, 832);
        framesMenuBackground = new TextureRegion[10];
        framesMenuBackground[0] = menuBackGroundRegionBy1440[0][0];
        framesMenuBackground[1] = menuBackGroundRegionBy1440[1][0];
        framesMenuBackground[2] = menuBackGroundRegionBy1440[2][0];
        framesMenuBackground[3] = menuBackGroundRegionBy1440[3][0];
        framesMenuBackground[4] = menuBackGroundRegionBy1440[4][0];
        framesMenuBackground[5] = menuBackGroundRegionBy1440[5][0];
        framesMenuBackground[6] = menuBackGroundRegionBy1440[6][0];
        framesMenuBackground[7] = menuBackGroundRegionBy1440[7][0];
        framesMenuBackground[8] = menuBackGroundRegionBy1440[8][0];
        framesMenuBackground[9] = menuBackGroundRegionBy1440[9][0];

        animBackground = new Animation<>(7f/60f, framesMenuBackground);

        playOnNet = mainMenuRegionBy256[0][0];
        playLocal = mainMenuRegionBy256[1][0];
        playOnNetJumbled = mainMenuRegionBy256[2][0];
        playLocalJumbled = mainMenuRegionBy256[3][0];
        quit = mainMenuRegionBy128[4][0];
        quitJumbled = mainMenuRegionBy128[4][1];
        speakerOn = mainMenuRegionBy32[17][0];
        speakerOff = mainMenuRegionBy32[17][1];

        playLocalBtn = new GameButton(462, 500, 512,64, true, playLocal);
        playOnNetBtn = new GameButton(462, 400, 512,64, true, playOnNet);
        quitBtn = new GameButton(618, 300, 224, 64, true, quit);
        speaker = new GameButton(1312, 720, 64, 64, true, speakerOn);

        framesRightArrow = new TextureRegion[10];
        framesRightArrow[0] = mainMenuRegionBy64[17][2];
        framesRightArrow[1] = mainMenuRegionBy64[17][2];
        framesRightArrow[2] = mainMenuRegionBy64[17][1];
        framesRightArrow[3] = mainMenuRegionBy64[17][3];
        framesRightArrow[4] = mainMenuRegionBy64[18][1];
        framesRightArrow[5] = mainMenuRegionBy64[18][2];
        framesRightArrow[6] = mainMenuRegionBy64[18][3];
        framesRightArrow[7] = mainMenuRegionBy64[18][2];
        framesRightArrow[8] = mainMenuRegionBy64[18][1];
        framesRightArrow[9] = mainMenuRegionBy64[17][3];

        animRightArrow = new Animation<>(5f/60f, framesRightArrow);

        framesLeftArrow = new TextureRegion[10];
        framesLeftArrow[0] = mainMenuRegionBy64[19][3];
        framesLeftArrow[1] = mainMenuRegionBy64[19][2];
        framesLeftArrow[2] = mainMenuRegionBy64[19][3];
        framesLeftArrow[3] = mainMenuRegionBy64[19][1];
        framesLeftArrow[4] = mainMenuRegionBy64[20][3];
        framesLeftArrow[5] = mainMenuRegionBy64[20][2];
        framesLeftArrow[6] = mainMenuRegionBy64[20][1];
        framesLeftArrow[7] = mainMenuRegionBy64[20][2];
        framesLeftArrow[8] = mainMenuRegionBy64[20][3];
        framesLeftArrow[9] = mainMenuRegionBy64[19][1];

        animLeftArrow = new Animation<>(5f/60f, framesLeftArrow);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        update(delta);
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentRightArrowFrame = animRightArrow.getKeyFrame(elapsedTime, true);
        TextureRegion currentLeftArrowFrame = animLeftArrow.getKeyFrame(elapsedTime, true);
        TextureRegion currentBackgroundFrame = animBackground.getKeyFrame(elapsedTime, true);

        Vector3 mousePosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        gameApplication.spriteBatch.begin();
//        gameApplication.getSpriteBatch().draw(menuBackground, 0, 0); // single image, use if animated background too demanding
        gameApplication.getSpriteBatch().draw(currentBackgroundFrame,0, 0, 1440f, 832f);

        ifHoveredDrawArrowsAroundPlayLocalButton(mousePosition, currentRightArrowFrame, currentLeftArrowFrame);
        ifHoveredDrawArrowsAroundPlayOnNetButton(mousePosition, currentRightArrowFrame, currentLeftArrowFrame);
        ifHoveredDrawArrowsAroundQuitButton(mousePosition, currentRightArrowFrame, currentLeftArrowFrame);

        gameApplication.getSpriteBatch().draw(playLocalBtn.getTexture(), playLocalBtn.getX(), playLocalBtn.getY(), playLocalBtn.getWidth(), playLocalBtn.getHeight());
        gameApplication.getSpriteBatch().draw(playOnNetBtn.getTexture(), playOnNetBtn.getX(), playOnNetBtn.getY(), playOnNetBtn.getWidth(), playOnNetBtn.getHeight());
        gameApplication.getSpriteBatch().draw(quitBtn.getTexture(), quitBtn.getX(), quitBtn.getY(), quitBtn.getWidth(), quitBtn.getHeight());
        gameApplication.getSpriteBatch().draw(speaker.getTexture(), speaker.getX(), speaker.getY(), speaker.getWidth(), speaker.getHeight());

        gameApplication.spriteBatch.end();
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameApplication.gameScreenManager.setScreen(GameScreenManager.STATE.MENU);
        }

        if (Gdx.input.justTouched()) {
            Vector3 mousePosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
//            System.out.println("STANDARD: (" + Math.round(mousePosition.x) + ", " + Math.round(mousePosition.y) + ")");

            if (playLocalBtn.isMouseOnButton(mousePosition)) playLocalButtonHasBeenClicked();
            if (playOnNetBtn.isMouseOnButton(mousePosition)) playOnNetButtonHasBeenClicked();
            if (quitBtn.isMouseOnButton(mousePosition)) quitHasBeenClicked();
            if (speaker.isMouseOnButton(mousePosition)) speakerButtonHasBeenClicked();

        }
    }

    private void playLocalButtonHasBeenClicked() {
        gameApplication.gameScreenManager.setScreen(GameScreenManager.STATE.LOCAL_SETUP);
    }

    private void playOnNetButtonHasBeenClicked() {

    }

    private void quitHasBeenClicked() {
        Gdx.app.exit();
    }

    private void speakerButtonHasBeenClicked() {
        if (musicPlaying) {
            musicPlaying = false;
            menuMusic.stop();
            speaker.setTexture(speakerOff);
        } else {
            musicPlaying = true;
            menuMusic.play();
            speaker.setTexture(speakerOn);
        }
    }

    private void ifHoveredDrawArrowsAroundPlayLocalButton(Vector3 hover,  TextureRegion currentRightArrowFrame, TextureRegion currentLeftArrowFrame) {
        if(playLocalBtn.isMouseOnButton(hover)) {
            playLocalBtn.setTexture(playLocalJumbled);
            gameApplication.getSpriteBatch().draw(currentRightArrowFrame, playLocalBtn.getX()+arrowRightPadX, playLocalBtn.getY(), 128, 64);
            gameApplication.getSpriteBatch().draw(currentLeftArrowFrame, playLocalBtn.getX()+ playLocalBtn.getWidth()+arrowLeftPadX, playLocalBtn.getY(), 128, 64);
        }else{
            playLocalBtn.setTexture(playLocal);
        }
    }

    private void ifHoveredDrawArrowsAroundPlayOnNetButton(Vector3 hover, TextureRegion currentRightArrowFrame, TextureRegion currentLeftArrowFrame) {
        if(playOnNetBtn.isMouseOnButton(hover)) {
            playOnNetBtn.setTexture(playOnNetJumbled);
            gameApplication.getSpriteBatch().draw(currentRightArrowFrame, playOnNetBtn.getX()+arrowRightPadX, playOnNetBtn.getY(), 128, 64);
            gameApplication.getSpriteBatch().draw(currentLeftArrowFrame, playOnNetBtn.getX()+ playOnNetBtn.getWidth()+arrowLeftPadX, playOnNetBtn.getY(), 128, 64);
        }else{
            playOnNetBtn.setTexture(playOnNet);
        }
    }

    private void ifHoveredDrawArrowsAroundQuitButton(Vector3 hover, TextureRegion currentRightArrowFrame, TextureRegion currentLeftArrowFrame) {
        if (quitBtn.isMouseOnButton(hover)) {
            quitBtn.setTexture(quitJumbled);
            gameApplication.getSpriteBatch().draw(currentRightArrowFrame, quitBtn.getX()+arrowRightPadX, quitBtn.getY(), 128, 64);
            gameApplication.getSpriteBatch().draw(currentLeftArrowFrame, quitBtn.getX()+quitBtn.getWidth()+arrowLeftPadX, quitBtn.getY(), 128, 64);
        }else{
            quitBtn.setTexture(quit);
        }
    }

    @Override
    public void show() {
        if (musicPlaying) menuMusic.play();
    }

    @Override
    public void hide() {
        menuMusic.stop();
    }

    @Override
    public void dispose() {
        menuBackground.dispose();
        mainMenuTexture.dispose();
        menuBackgroundSpriteSheet.dispose();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

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
    public boolean keyTyped(char c) {
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

