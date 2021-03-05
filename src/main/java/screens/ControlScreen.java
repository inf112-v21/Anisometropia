package screens;

import actor.Player;
import cards.RegisterCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import logic.GameLogic;

import java.util.ArrayList;
import java.util.Collections;

import static logic.GameLogic.gameOver;
import static logic.GameLogic.cardExecutionInProgress;

public class ControlScreen extends InputAdapter {
    GameLogic gameLogic;

    private final SpriteBatch batch;
    private final Texture damageToken;
    private final Texture powerDownButton;
    private final Texture lifeToken;

    private final float cardWidth = 64, cardHeight = 64;

    float amountToMoveCard = 64;

    // Variables used to position dealt register cards.
    int[] cardX = new int[9];
    int[] cardY = new int[9];
    boolean[] isCardChosen = new boolean[9];
    ArrayList<RegisterCard> chosenCards;
    int numCardsChosen;
    BitmapFont smallFont, bigFont;
    GameButton acceptButton, progressButton;

    public ControlScreen(GameLogic gameLogic) {
        batch = new SpriteBatch();

        smallFont = new BitmapFont();
        smallFont.setColor(Color.BLACK);
        smallFont.getData().setScale(1.5f);
        bigFont = new BitmapFont();
        bigFont.getData().setScale(5f);

        acceptButton = new GameButton(584, 0, 128, 128, false, new Texture(Gdx.files.internal("accept_card_selection_unavailable.png")));
        progressButton = new GameButton(732, 0, 128, 128, false, new Texture(Gdx.files.internal("click_to_progress_unavailable.png")));

        damageToken = new Texture(Gdx.files.internal("damageToken.png"));
        powerDownButton = new Texture(Gdx.files.internal("powerDown.png"));
        lifeToken = new Texture(Gdx.files.internal("lifeToken.png"));

        initializeCards();

        this.gameLogic = gameLogic;

        Gdx.input.setInputProcessor(this);
    }

    public void render(OrthographicCamera camera) {
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
            }
            if(click.x > acceptButton.getX() && click.x < (acceptButton.getX() + acceptButton.getWidth()) &&
               click.y > acceptButton.getY() && click.y < (acceptButton.getY() + acceptButton.getHeight())) {
                acceptButtonHasBeenClicked();
            }
            if(click.x > progressButton.getX() && click.x < (progressButton.getX() + progressButton.getWidth()) &&
               click.y > progressButton.getY() && click.y < (progressButton.getY() + progressButton.getHeight())) {
                progressButtonHasBeenClicked();
            }

        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(acceptButton.getTexture(), acceptButton.getX(), acceptButton.getY(), acceptButton.getWidth(), acceptButton.getHeight());
        batch.draw(progressButton.getTexture(), progressButton.getX(), progressButton.getY(), progressButton.getWidth(), progressButton.getHeight());

        if(!cardExecutionInProgress) drawCardsOfCurrentPlayer(batch);
        drawCurrentPlayerName(batch);
        drawPowerDownButton(batch);
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
                "r: restart the game\n" +
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
            cardX[cardIndex] = cardIndex*64;
            numCardsChosen--;
        }
        else if (numCardsChosen < 5) {
            isCardChosen[cardIndex] = true;
            cardY[cardIndex] -= amountToMoveCard;
            cardX[cardIndex] = numCardsChosen*64;
            numCardsChosen++;
            chosenCards.set(numCardsChosen - 1, gameLogic.getCurrentPlayer().getDealtRegisterCards().get(cardIndex));
            if (numCardsChosen == 5) {
                acceptButton.setActive(true);
                acceptButton.setTexture(new Texture(Gdx.files.internal("accept_card_selection.png")));
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
        chosenCards = new ArrayList<>(Collections.nCopies(5,
                      new RegisterCard("", 0, true)));
        numCardsChosen = 0;
    }

    private void acceptButtonHasBeenClicked() {
        if (acceptButton.isActive) {
            acceptButton.setActive(false);
            acceptButton.setTexture(new Texture(Gdx.files.internal("accept_card_selection_unavailable.png")));

            gameLogic.finishTurn(chosenCards);
            if (gameLogic.getCurrentPlayer() == gameLogic.getLastPlayer()){
                progressButton.setActive(true);
                progressButton.setTexture(new Texture(Gdx.files.internal("click_to_progress.png")));
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
                progressButton.setTexture(new Texture(Gdx.files.internal("click_to_progress_unavailable.png")));
            }
        }
    }

    private void drawCardsOfCurrentPlayer(SpriteBatch batch) {
        for (int i = 0; i < gameLogic.getCurrentPlayer().getDealtRegisterCards().size(); i++) {
            RegisterCard registerCard = gameLogic.getCurrentPlayer().getDealtRegisterCards().get(i);
            Texture cardTexture = new Texture(Gdx.files.internal(registerCard.getGraphicLocation()));
            batch.draw(cardTexture, cardX[i], cardY[i], cardWidth, cardHeight);
        }
    }

    private void drawPowerDownButton(SpriteBatch batch) {
        batch.draw(powerDownButton, 850, -50, 200,200);
    }

    private void drawDamageTokensOfCurrentPlayer(SpriteBatch batch) {
        for (int i = 10; i > gameLogic.getCurrentPlayer().getDmgTokens(); i--) {
            batch.draw(damageToken, 816-(i*64), 132, 80, 80);
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
        damageToken.dispose();
        powerDownButton.dispose();
        acceptButton.getTexture().dispose();
        progressButton.getTexture().dispose();
        lifeToken.dispose();
        batch.dispose();
        smallFont.dispose();
    }
}