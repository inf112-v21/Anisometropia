package screens;

import blueprinting.DrawThis;
import blueprinting.WriteThis;
import cards.RegisterCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import logic.GameLogic;

import java.util.ArrayList;
import java.util.Collections;

import static logic.GameLogic.gameOver;

public class ControlScreen extends InputAdapter {
    DrawThis drawThis;
    WriteThis writeThis;
    GameLogic gameLogic;

    private final SpriteBatch batch;
    private final Texture damageToken;
    private final Texture powerDownButton;
    private final Texture lifeToken;

    private final float cardWidth = 84, cardHeight = 96;

    float amountToMoveCard = 174;

    // Variables used to position dealt register cards.
    int[] cardX = new int[9];
    int[] cardY = new int[9];
    boolean[] isCardChosen = new boolean[9];
    ArrayList<RegisterCard> chosenCards;
    int numCardsChosen;

    public ControlScreen(GameLogic gameLogic) {

        batch = new SpriteBatch();

        damageToken = new Texture(Gdx.files.internal("damageToken.png"));
        powerDownButton = new Texture(Gdx.files.internal("powerDown.png"));
        lifeToken = new Texture(Gdx.files.internal("lifeToken.png"));

        initializeCards();

        drawThis = new DrawThis();
        writeThis = new WriteThis();

        this.gameLogic = gameLogic;

        Gdx.input.setInputProcessor(this);
    }

    public void render(OrthographicCamera camera) {
        // Prints out the coordinates of the position clicked
        if (Gdx.input.justTouched()) {
            Vector3 clickPosition = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
//            System.out.println("(" + Math.round(clickPosition.x) + ", " + Math.round(clickPosition.y) + ")");

            if (!gameOver) {
                // Relates mouse clicks to particular cards.
                for (int i = 0; i < gameLogic.getCurrentPlayer().getDealtRegisterCards().size(); i++) {
                    if (clickPosition.x > cardX[i] && clickPosition.x < cardX[i] + cardWidth
                            && clickPosition.y > cardY[i] && clickPosition.y < cardY[i] + cardHeight) {
                        thisCardWasClicked(i);
                        break;
                    }
                }
                if (numCardsChosen == 5) {
                    gameLogic.finishTurn(chosenCards);
                    initializeCards();
                }
            }
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(powerDownButton, 730, 115, 200,200);

        drawDamageTokensOfCurrentPlayer(batch);
        drawLifeTokensOfCurrentPlayer(batch);
        drawCardsOfCurrentPlayer(batch);

        batch.end();
    }

    /**
     * Moves clicked-on card to chosen section. If already in the chosen section, moves back to original position.
     * @param cardIndex
     */
    private void thisCardWasClicked(int cardIndex) {
        if (isCardChosen[cardIndex]) {
            isCardChosen[cardIndex] = false;
            adjustPositionOfChosenCards(cardX[cardIndex]);
            cardY[cardIndex] += amountToMoveCard;
            cardX[cardIndex] = cardIndex*84;
            numCardsChosen--;
        }
        else {
            isCardChosen[cardIndex] = true;
            cardY[cardIndex] -= amountToMoveCard;
            cardX[cardIndex] = numCardsChosen*108;
            numCardsChosen++;
            chosenCards.set(numCardsChosen - 1, gameLogic.getCurrentPlayer().getDealtRegisterCards().get(cardIndex));
        }
    }

    /**
     * Visually shifts cards that were to the right of the deselected card to the left.
     * @param deselectedCardX X-coordinate of the deselected card.
     */
    private void adjustPositionOfChosenCards(int deselectedCardX) {
        for (int i = 0; i < 9; i++) {
            if (isCardChosen[i] && cardX[i] > deselectedCardX) {
                cardX[i] -= 108;
            }
        }
    }

    private void drawDamageTokensOfCurrentPlayer(SpriteBatch batch) {
        for (int i = 10; i > gameLogic.getCurrentPlayer().getDmgTokens(); i--) {
            batch.draw(damageToken, 620-(i*64), 96, 80, 80);
        }
    }

    private void drawLifeTokensOfCurrentPlayer(SpriteBatch batch) {
        for (int i = 0; i < gameLogic.getCurrentPlayer().getLifeTokens(); i++){
            batch.draw(lifeToken,860+(i*72), 152, 128,148);
        }
    }

    private void drawCardsOfCurrentPlayer(SpriteBatch batch) {
        for (int i = 0; i < gameLogic.getCurrentPlayer().getDealtRegisterCards().size(); i++) {
            RegisterCard registerCard = gameLogic.getCurrentPlayer().getDealtRegisterCards().get(i);
            Texture cardTexture = new Texture(Gdx.files.internal(registerCard.getGraphicLocation()));
            batch.draw(cardTexture, cardX[i], cardY[i], cardWidth, cardHeight);
        }
    }

    /**
     * Sets initial values for dealt and chosen register cards.
     */
    private void initializeCards() {
        for (int i = 0; i < 9; i++) {
            cardX[i] = i*84;
            cardY[i] = 174;
            isCardChosen[i] = false;
        }
        chosenCards = new ArrayList<>(Collections.nCopies(5,
                      new RegisterCard("", 0, true)));
        numCardsChosen = 0;
    }

    public void dispose() {
        damageToken.dispose();
        powerDownButton.dispose();
        lifeToken.dispose();
        batch.dispose();
    }
}