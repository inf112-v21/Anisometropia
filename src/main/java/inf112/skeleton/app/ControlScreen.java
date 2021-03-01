package inf112.skeleton.app;

import blueprinting.DrawThis;
import blueprinting.WriteThis;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static inf112.skeleton.app.GameLogic.gameOver;

public class ControlScreen extends InputAdapter { // can extend InputAdapter if we only want some of the methods
    DrawThis drawThis;
    WriteThis writeThis;

    GameLogic gameLogic;

    private final SpriteBatch batch;

    private final Texture damageToken;
    private final Texture powerDownButton;
    private final Texture lifeToken;

    private final float cardWidth = 84, cardHeight = 96;

    float amountToMoveCard = 174;

    int[] cardX = new int[9];
    int[] cardY = new int[9];

    ArrayList<RegisterCard> chosenCards = new ArrayList<>(Collections.nCopies(5,
            new RegisterCard("", 0, true)));
    int numCardsChosen = 0;
    boolean[] isCardChosen = new boolean[9];

    public ControlScreen(GameLogic gameLogic) {

        batch = new SpriteBatch();

        damageToken = new Texture(Gdx.files.internal("damageToken.png"));
        powerDownButton = new Texture(Gdx.files.internal("powerDown.png"));
        lifeToken = new Texture(Gdx.files.internal("lifeToken.png"));

        for (int i = 0; i < 9; i++) {
            cardY[i] = 174;
            cardX[i] = i*84;
            isCardChosen[i] = false;
        }

        drawThis = new DrawThis();
        writeThis = new WriteThis();

        this.gameLogic = gameLogic;

        Gdx.input.setInputProcessor(this);
    }

    public void render(OrthographicCamera camera) {
        // Prints out the coordinates of the position clicked
        if (Gdx.input.justTouched()) {
            Vector3 clickPosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println("(" + Math.round(clickPosition.x) + ", " + Math.round(clickPosition.y) + ")");

            if (!gameOver) {

                // Stores and moves chosen cards.
                for (int i = 0; i < 9; i++) {
                    if (clickPosition.x > cardX[i] && clickPosition.x < cardX[i] + cardWidth
                        && clickPosition.y > cardY[i] && clickPosition.y < cardY[i] + cardHeight) {
                        if (isCardChosen[i]) {
                            isCardChosen[i] = false;
                            cardY[i] += amountToMoveCard;
                            numCardsChosen--;
                        }
                        else {
                            isCardChosen[i] = true;
                            cardY[i] -= amountToMoveCard;
                            numCardsChosen++;
                            chosenCards.set(numCardsChosen - 1, gameLogic.getCurrentPlayer().getDealtRegisterCards().get(i));
                        }
                    }
                    if (numCardsChosen == 5) {
                        gameLogic.getCurrentPlayer().setChosenRegisterCards(chosenCards);
                        chosenCards = new ArrayList<>(Collections.nCopies(5,
                                      new RegisterCard("", 0, true)));
                        numCardsChosen = 0;
                    }
                }

                /*
                Brukes som mal en liten stund, vil fjernes snart.
                if (clickPosition.x > move1X && clickPosition.x < move1X + cardWidth && clickPosition.y > move1Y && clickPosition.y < move1Y + cardHeight) {
                    if (move1Chosen) {
                        move1Chosen = false;
                        move1Y += amountToMoveCard;
                        gameLogic.getCurrentPlayer().moveByDirection(1);
                        gameLogic.getPlayerQueue().next();
                        gameLogic.setTurnOverToTrue();
                    } else {
                        move1Chosen = true;
                        move1Y -= amountToMoveCard;
                    }
                }
                */
            }
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int i = 10; i > gameLogic.getCurrentPlayer().getDmgTokens(); i--) {
            batch.draw(damageToken, 620-(i*64), 96, 80, 80);
        }

        batch.draw(powerDownButton, 730, 115, 200,200);

        for (int i = 0; i < gameLogic.getCurrentPlayer().getLifeTokens(); i++){
            batch.draw(lifeToken,860+(i*72), 152, 128,148);
        }

        // Draws textures of cards dealt to current player.
        for (int i = 0; i < gameLogic.getCurrentPlayer().getDealtRegisterCards().size(); i++) {
            RegisterCard registerCard = gameLogic.getCurrentPlayer().getDealtRegisterCards().get(i);
            Texture cardTexture = new Texture(Gdx.files.internal(registerCard.getGraphicLocation()));
            batch.draw(cardTexture, cardX[i], cardY[i], cardWidth, cardHeight);
        }

        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }
}
