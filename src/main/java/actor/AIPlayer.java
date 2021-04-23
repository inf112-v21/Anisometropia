package actor;

import map.GameMap;

import java.util.ArrayList;

public class AIPlayer extends Player {

    public AIPlayer(int x, int y, String playerName, GameMap gameMap, int characterID) {
        super(x, y, playerName, gameMap,true, characterID, true);
    }

    @Override
    public void startCardDecisionWithAI() {
        chooseCardsFromDealtProgramCards();
    }

    /**
     * Will make the AI player use cards from dealtProgramCards
     */
    public void chooseCardsFromDealtProgramCards() {
        int numCardsAllowed = Math.min(9-dmgTokens, 5);
        chosenProgramCards = new ArrayList<>();
        for (int i = 0; i < numCardsAllowed; i++) {
            chosenProgramCards.add(dealtProgramCards.get(i));
        }
    }

}
