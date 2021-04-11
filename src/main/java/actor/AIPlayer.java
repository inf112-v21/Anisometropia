package actor;

import cards.ProgramCard;
import map.GameMap;

import java.awt.*;
import java.util.ArrayList;

public class AIPlayer extends Player {

    public AIPlayer(int x, int y, String playerName, GameMap gameMap, int characterID, boolean isAi) {
        super(x, y, playerName, gameMap, characterID, isAi);
    }

    @Override
    public void startCardDecisionWithAI() {
        chooseCardsFromDealtProgramCards();
//        chooseCardsThatWillGetPlayerClosestToNextFlag();
    }

    public void chooseCardsThatWillGetPlayerClosestToNextFlag() {
        Point nextFlagPosition = gameMap.getFlagPosition(numberOfFlagsReached());

        // end points

        // if(flagOnThisPosition) return 1;

        // if(laserOnThisPositions) return -1;

        // if(holeOnThisPositions) return -1;

        // else valuate distance to next flag

        // **** end of end points ****


        // minimax? with score, and cardSelection as "position"?

        // need to valuate with canMove() to add card

        //  distance formula = square( (x2 − x1)^2 + (y2 − y1)^2 )

        // if reaches flag, select selection (and need to add last cards if not full selection)


    }

    public int numberOfFlagsReached() {
        if (flagsReached[2]) return 3;
        if (flagsReached[1]) return 2;
        if (flagsReached[0]) return 1;
        else return 0;
    }

    public boolean canMove(int x, int y, int dx, int dy) {
        if(isPlayerDead()) return false;
        boolean wallOutOfPositionBlocked = false;
        boolean wallIntoPositionBlocked = false;
        if (gameMap.isThereWallOnThisPosition(x, y)) {
            if (!gameMap.getWall().checkOutOfWall(x, y, dx, dy)) {
                wallOutOfPositionBlocked = true;
            }
        }

        if (gameMap.isThereWallOnThisPosition(x + dx, y + dy)) {
            if (!gameMap.getWall().checkIntoWall(x, y, dx, dy)) {
                wallIntoPositionBlocked = true;
            }
        }
        if(wallIntoPositionBlocked || wallOutOfPositionBlocked){
            return false;
        }
        return (x + dx >= 0 && x + dx < gameMap.getWidth()) && (y + dy >= 0 && y + dy < gameMap.getHeight());
    }

    public void chooseCardsFromDealtProgramCards() {
        int numCardsAllowed = Math.min(9-dmgTokens, 5);
        chosenProgramCards = new ArrayList<>();

        chosenProgramCards.addAll(dealtProgramCards);
        for (ProgramCard card : chosenProgramCards) {
            System.out.println("card:"+card.toString());
            if(chosenProgramCards.size() == 5) return;
        }
    }


}
