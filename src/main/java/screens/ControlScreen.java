package screens;

import cards.RegisterCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import logic.GameLogic;
import p2p.Multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static logic.GameLogic.cardExecutionInProgress;
import static logic.GameLogic.gameOver;

public class ControlScreen extends InputAdapter {
    GameLogic gameLogic;

    //Variables used to draw objects on the map
    private final SpriteBatch batch;
    private final Texture damageToken;
    private final Texture lifeToken;
    private final Texture damageTokenPositionIndicator;
    private final ArrayList<Texture> registerCardTextures = new ArrayList<>();
    private final ArrayList<Texture> dealtRegisterCardTextures = new ArrayList<>();

    private final float cardWidth = 64, cardHeight = 64;

    float amountToMoveCard = 64;
    int choiceDeckOffset = 128;

    // Variables used to position dealt register cards.
    int[] cardX = new int[9];
    int[] cardY = new int[9];
    boolean[] isCardChosen = new boolean[9];
    ArrayList<RegisterCard> chosenCards;
    int numCardsChosen;
    BitmapFont smallFont, bigFont;

    //Variables used to create buttons
    TextureRegion[][] gameButtonsSpriteSheet, multiPlayerButtons, powerDownButtonRegion;
    TextureRegion acceptTexture, acceptTextureUnavailable, progressTexture, progressTextureUnavailable, borderTexture, borderTextureUnavailable, hostButtonTexture, joinButtonTexture,powerDownButtonTexture;

    GameButton acceptButton, progressButton, borderButton, hostButton, joinButton, powerDownButton;

    public ControlScreen(GameLogic gameLogic) {
        batch = new SpriteBatch();

        smallFont = new BitmapFont();
        smallFont.setColor(Color.BLACK);
        smallFont.getData().setScale(1.5f);
        bigFont = new BitmapFont();
        bigFont.getData().setScale(5f);

        gameButtonsSpriteSheet = TextureRegion.split(new Texture("gamebuttons_spritesheet.png"), 128, 128);
        progressTextureUnavailable = gameButtonsSpriteSheet[0][0];
        progressTexture = gameButtonsSpriteSheet[0][1];
        acceptTextureUnavailable = gameButtonsSpriteSheet[1][0];
        acceptTexture = gameButtonsSpriteSheet[1][1];
        borderTextureUnavailable = gameButtonsSpriteSheet[2][0];
        borderTexture = gameButtonsSpriteSheet[2][1];

        //Creating buttons needed to establish a connection to enable multiplayer
        multiPlayerButtons = TextureRegion.split(new Texture("multiPlayerButtons.png"), 400,400);
        hostButtonTexture = multiPlayerButtons[0][0];
        joinButtonTexture = multiPlayerButtons[0][1];

        //Creating power down button
        powerDownButtonRegion = TextureRegion.split(new Texture("powerDown.png"),800,800);
        powerDownButtonTexture = powerDownButtonRegion[0][0];
        powerDownButton = new GameButton(850, -50, 200,200, false, powerDownButtonTexture);

        acceptButton = new GameButton(584, 0, 128, 128, false, acceptTextureUnavailable);
        progressButton = new GameButton(732, 0, 128, 128, false, progressTextureUnavailable);
        borderButton = new GameButton(88,-16,400,128, false, borderTextureUnavailable);

        /*
          If the host button is pressed then this should call the MultiPlayer constructor and establish a connection.
          The join button is used for other players to connect to that server.
         */
        hostButton = new GameButton(1100,700, 85,85, true, hostButtonTexture);
        joinButton = new GameButton(1200, 700,85,85,true, joinButtonTexture);

        damageToken = new Texture(Gdx.files.internal("damageToken.png"));
        lifeToken = new Texture(Gdx.files.internal("lifeToken.png"));
        damageTokenPositionIndicator = new Texture(Gdx.files.classpath("damageTokenPositionIndicator.png"));

        registerCardTextures.add(new Texture(Gdx.files.internal("RegisterCardAssets/Move1.png")));
        registerCardTextures.add(new Texture(Gdx.files.internal("RegisterCardAssets/Move2.png")));
        registerCardTextures.add(new Texture(Gdx.files.internal("RegisterCardAssets/Move3.png")));
        registerCardTextures.add(new Texture(Gdx.files.internal("RegisterCardAssets/BackUp.png")));
        registerCardTextures.add(new Texture(Gdx.files.internal("RegisterCardAssets/RotateLeft.png")));
        registerCardTextures.add(new Texture(Gdx.files.internal("RegisterCardAssets/RotateRight.png")));
        registerCardTextures.add(new Texture(Gdx.files.internal("RegisterCardAssets/UTurn.png")));

        initializeCards();

        this.gameLogic = gameLogic;

        Gdx.input.setInputProcessor(this);
    }

    public void render(OrthographicCamera camera) throws IOException {
        // Prints out the coordinates of the position clicked
        if (Gdx.input.justTouched()) {
            Vector3 click = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
//            System.out.println("(" + Math.round(click.x) + ", " + Math.round(click.y) + ")");

            if (!gameOver) {
                if (!cardExecutionInProgress) {
                    // Relates mouse clicks to particular cards.
                    for (int i = 0; i < gameLogic.getCurrentPlayer().getDealtRegisterCards().size(); i++) {
                        if (click.x > cardX[i] && click.x < cardX[i] + cardWidth &&
                                click.y > cardY[i] && click.y < cardY[i] + cardHeight) {
                            thisCardWasClicked(i);
                            break;
                        }
                    }
                }
                if (click.x > acceptButton.getX() && click.x < (acceptButton.getX() + acceptButton.getWidth()) &&
                        click.y > acceptButton.getY() && click.y < (acceptButton.getY() + acceptButton.getHeight())) {
                    acceptButtonHasBeenClicked();
                }
                if (click.x > progressButton.getX() && click.x < (progressButton.getX() + progressButton.getWidth()) &&
                        click.y > progressButton.getY() && click.y < (progressButton.getY() + progressButton.getHeight())) {
                    progressButtonHasBeenClicked();
                }
                if (click.x > powerDownButton.getX() && click.x < (powerDownButton.getX() + powerDownButton.getWidth()) &&
                        click.y > powerDownButton.getY() && click.y < (powerDownButton.getY() + powerDownButton.getHeight())) {
                    gameLogic.getCurrentPlayer().powerDownRobot();
                }
                if (click.x > joinButton.getX() && click.x < (joinButton.getX() + joinButton.getWidth()) &&
                        click.y > joinButton.getY() && click.y < (joinButton.getY() + joinButton.getHeight())) {
                    joinButtonHasBeenClicked();
                }
                if (click.x > hostButton.getX() && click.x < (hostButton.getX() + hostButton.getWidth()) &&
                        click.y > hostButton.getY() && click.y < (hostButton.getY() + hostButton.getHeight())) {
                    hostButtonHasBeenClicked();
                }
            }
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(acceptButton.getTexture(), acceptButton.getX(), acceptButton.getY(), acceptButton.getWidth(), acceptButton.getHeight());
        batch.draw(progressButton.getTexture(), progressButton.getX(), progressButton.getY(), progressButton.getWidth(), progressButton.getHeight());
        batch.draw(borderButton.getTexture(), borderButton.getX(), borderButton.getY(), borderButton.getWidth(), borderButton.getHeight());
        batch.draw(hostButton.getTexture(),hostButton.getX(),hostButton.getY(),hostButton.getWidth(),hostButton.getHeight());
        batch.draw(joinButton.getTexture(),joinButton.getX(),joinButton.getY(),joinButton.getWidth(),joinButton.getHeight());
        batch.draw(powerDownButton.getTexture(), powerDownButton.getX(), powerDownButton.getY(),powerDownButton.getWidth(),powerDownButton.getHeight());

        if(!cardExecutionInProgress) drawCardsOfCurrentPlayer(batch);
        drawCurrentPlayerName(batch);
        drawDamageTokenPositionIndicators(batch);
        drawDamageTokensOfCurrentPlayer(batch);
        drawLifeTokensOfCurrentPlayer(batch);

        for (int i = 0; i < gameLogic.getPlayerQueue().getPlayerQueue().size(); i++) {
            smallFont.draw(batch,gameLogic.getPlayerQueue().getPlayerQueue().get(i).playerName,1200, 620-(i*32));
            if(gameLogic.getPlayerQueue().getPlayerQueue().get(i).equals(gameLogic.getCurrentPlayer())) smallFont.draw(batch,">>>",1130, 620-(i*32));
        }

        if(GameLogic.gameOver) bigFont.draw(batch, GameLogic.gameMessage, 0, 56);

        smallFont.draw(batch,"1. click cards to select/deselect\n" +
                "2. after choosing 5, accept\n" +
                "3. next player turn starts\n" +
                "4. click to progress actions\n" +
                "\n" +
                "R:              restart the game\n" +
                "ESCAPE:  exit", 1100, 200);

        batch.end();
    }

    /**
     * Moves clicked-on card to chosen section. If already in the chosen section, moves back to original position.
     * @param cardIndex Index of dealt card as presented when dealt.
     */
    private void thisCardWasClicked(int cardIndex) {
        if (isCardChosen[cardIndex]) {
            isCardChosen[cardIndex] = false;
            adjustPositionOfChosenCards(cardX[cardIndex]);
            cardY[cardIndex] += amountToMoveCard;
            cardX[cardIndex] = (cardIndex*64);
            numCardsChosen--;
            if (numCardsChosen == 4) {
                acceptButton.setActive(false);
                acceptButton.setTexture(acceptTextureUnavailable);
                borderButton.setTexture(borderTextureUnavailable);
            }
        }
        else if (numCardsChosen < 5) {
            isCardChosen[cardIndex] = true;
            cardY[cardIndex] -= amountToMoveCard;
            cardX[cardIndex] = choiceDeckOffset+(numCardsChosen*64);
            numCardsChosen++;
            chosenCards.set(numCardsChosen - 1, gameLogic.getCurrentPlayer().getDealtRegisterCards().get(cardIndex));
            if (numCardsChosen == 5) {
                acceptButton.setActive(true);
                acceptButton.setTexture(acceptTexture);
                borderButton.setTexture(borderTexture);
            }
        }
    }

    /**
     * Visually shifts cards that were to the right of the deselected card to the left.
     * @param deselectedCardX X-coordinate of the deselected card.
     */
    private void adjustPositionOfChosenCards(int deselectedCardX) {
        for (int i = 0; i < 9; i++) {
            if (isCardChosen[i] && cardX[i] > deselectedCardX) {
                cardX[i] -= 64;
            }
        }
    }

    /**
     * Sets initial values for dealt and chosen register cards.
     */
    private void initializeCards() {
        for (int i = 0; i < 9; i++) {
            cardX[i] = i*64;
            cardY[i] = 64;
            isCardChosen[i] = false;
        }
        dealtRegisterCardTextures.clear();
        chosenCards = new ArrayList<>(Collections.nCopies(5,
                new RegisterCard(0, 0, true)));
        numCardsChosen = 0;
    }

    private void acceptButtonHasBeenClicked() throws IOException {
        if (acceptButton.isActive) {
            acceptButton.setActive(false);
            acceptButton.setTexture(acceptTextureUnavailable);
            borderButton.setTexture(borderTextureUnavailable);

            gameLogic.finishTurn(chosenCards);
            if (gameLogic.getCurrentPlayer() == gameLogic.getLastPlayer()){
                progressButton.setActive(true);
                progressButton.setTexture(progressTexture);
                gameLogic.getPlayerQueue().next();
                cardExecutionInProgress = true;
            } else {
                gameLogic.getPlayerQueue().next();
            }
            initializeCards();
        }
    }

    private void progressButtonHasBeenClicked() {
        if (progressButton.isActive) {

            gameLogic.executeCard();

            if (!cardExecutionInProgress) {
                progressButton.setActive(false);
                progressButton.setTexture(progressTextureUnavailable);
            }
        }
    }

    private void hostButtonHasBeenClicked() throws IOException {
        if (hostButton.isActive) {
            gameLogic.mp = new Multiplayer(Boolean.TRUE);
            Thread mpThread = new Thread(gameLogic.mp);
            mpThread.start();
            hostButton.setActive(false);
            joinButton.setActive(false);
        }
    }

    private void joinButtonHasBeenClicked() throws IOException {
        if (joinButton.isActive) {
            gameLogic.mp = new Multiplayer(Boolean.FALSE);
            Thread mpThread = new Thread(gameLogic.mp);
            mpThread.start();
            hostButton.setActive(false);
            joinButton.setActive(false);
        }
    }

    private void drawCardsOfCurrentPlayer(SpriteBatch batch) {
        for (int i = 0; i < gameLogic.getCurrentPlayer().getDealtRegisterCards().size(); i++) {
            RegisterCard registerCard = gameLogic.getCurrentPlayer().getDealtRegisterCards().get(i);
            dealtRegisterCardTextures.add(registerCardTextures.get(registerCard.getCardType()));
            batch.draw(dealtRegisterCardTextures.get(i), cardX[i], cardY[i], cardWidth, cardHeight);
        }
    }

    private void drawDamageTokensOfCurrentPlayer(SpriteBatch batch) {
        for (int i = gameLogic.getCurrentPlayer().getDmgTokens(); i > 0; i--) {
            batch.draw(damageToken, 816-(i*64), 132, 80, 80);
        }
    }

    private void drawDamageTokenPositionIndicators(SpriteBatch batch) {
        for (int i = 10; i > 0; i--) {
            batch.draw(damageTokenPositionIndicator, 816-(i*64), 132, 80, 80);
        }
    }

    private void drawLifeTokensOfCurrentPlayer(SpriteBatch batch) {
        for (int i = 0; i < gameLogic.getCurrentPlayer().getLifeTokens(); i++){
            batch.draw(lifeToken,812+(i*72), 96, 128,148);
        }
    }

    private void drawCurrentPlayerName(SpriteBatch batch) {
        smallFont.draw(batch, "current player:\n" + gameLogic.getCurrentPlayer().playerName, 0, 200);
    }

    public void dispose() {
        damageTokenPositionIndicator.dispose();
        damageToken.dispose();
        powerDownButtonTexture.getTexture().dispose();
        joinButtonTexture.getTexture().dispose();
        hostButtonTexture.getTexture().dispose();
        acceptTexture.getTexture().dispose();
        acceptTextureUnavailable.getTexture().dispose();
        progressTexture.getTexture().dispose();
        progressTextureUnavailable.getTexture().dispose();
        borderTexture.getTexture().dispose();
        borderTextureUnavailable.getTexture().dispose();
        lifeToken.dispose();
        batch.dispose();
        smallFont.dispose();
        for (Texture regCardTexture : dealtRegisterCardTextures) {
            regCardTexture.dispose();
        }
    }
}