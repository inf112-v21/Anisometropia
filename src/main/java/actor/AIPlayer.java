package actor;

import cards.ProgramCard;
import map.GameMap;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AIPlayer extends Player {

    public AIPlayer(int x, int y, String playerName, GameMap gameMap, int characterID) {
        super(x, y, playerName, gameMap, characterID, true);
    }

    @Override
    public void startCardDecisionWithAI() {
        // *** TIMER START ***
        System.out.print(playerName+" is thinking...");
        long startTime = System.nanoTime();
        // *** TIMER START *** *END*

        // TODO: take into account the locked cards (possible to just change "depth" to amountOfLockedCards ??? )
        // TODO: if (dmgTokens > 6) announcePowerDown();
        // TODO: take into account conveyors++++++ (or does this only happen after 5 rounds???)
//        chooseCardsFromDealtProgramCards();


        // GETTING THE BEST CARD CHOICES
        int[] bestChoice = chooseCardsThatWillGetPlayerClosestToNextFlag(x, y, direction, 5, new int[]{-1,-1,-1,-1,-1}, dealtProgramCards);


        // *** TIMER STOP ***
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.print(" and made a decision after "+totalTime / 1000000000+" seconds!\n");
        // *** TIMER STOP *** *END*

        // END LOG
        System.out.print("The AI chose between these cards: ");
        for (ProgramCard card : dealtProgramCards) {
            System.out.print(card.getCardType()+", ");
        }
        System.out.print("\n and ended up with these choices: ");
        for (int j = 5; j < bestChoice.length; j++) {
            if(j == 9) System.out.print(bestChoice[j]+"\n");
            else System.out.print(bestChoice[j]+", ");
        }
        System.out.println("               and the score was: "+bestChoice[0]);

        chosenProgramCards = new ArrayList<>();

        for (int j = 5; j < bestChoice.length; j++) {
            switch (bestChoice[j]) {
                case 0: chosenProgramCards.add(new ProgramCard(0, 1, true)); break;
                case 1: chosenProgramCards.add(new ProgramCard(1, 2, true)); break;
                case 2: chosenProgramCards.add(new ProgramCard(2, 3, true)); break;
                case 3: chosenProgramCards.add(new ProgramCard(3, -1, true)); break;
                case 4: chosenProgramCards.add(new ProgramCard(4, 3, false)); break;
                case 5: chosenProgramCards.add(new ProgramCard(5, 1, false)); break;
                case 6: chosenProgramCards.add(new ProgramCard(6, 2, false)); break;
            }
        }
    }

    public int[] chooseCardsThatWillGetPlayerClosestToNextFlag(int x, int y, int dir, int depth, int[] choices, ArrayList<ProgramCard> cards) {
        Point nextFlagPosition = gameMap.getFlagPosition(numberOfFlagsReached());
        //             return: score,              x,  y, dir, depth, c0,         c1,         c2,         c3,         c4
        int[] best = new int[]{Integer.MAX_VALUE, -1, -1,  -1,    -1, choices[0], choices[1], choices[2], choices[3], choices[4]};

        int score = 0;

        if(gameMap.isThereHoleOnThisPosition(x,y)) score += 200;

        if(gameMap.isThereFlagHere(x,y)) score -= 100;

        if(gameMap.isThereLaserBeamsOnThisPosition(x,y)) score += 20;

        if(depth == 0) {
            score += calculateDistance(x,y, (int) nextFlagPosition.getX(), (int) nextFlagPosition.getY());
            best[0] = score;
            return best;
        }

        for (ProgramCard card : cards) {
              if (card.getCardType() < 3) {
                for (int i = 0; i < card.amountToMoveOrRotate; i++) {
                    if (canMove(x, y, dir)) {
                        int[] cardOutcome = moveByDirection(x,y, 1, dir); // returns (x, y, dir)
                        x = cardOutcome[0];
                        y = cardOutcome[1];
                    } else return best;
                }
            } else {
                int[] cardOutcome = applyCardProperties(card.getCardType(), x, y, dir); // returns (x, y, dir);
                x = cardOutcome[0];
                y = cardOutcome[1];
            }

            ArrayList<ProgramCard> copiedCards = new ArrayList<>(cards);
            int cardToUse = card.getCardType();
            copiedCards.remove(card);

            choices[5-depth] = cardToUse;

            int[] result = chooseCardsThatWillGetPlayerClosestToNextFlag(x, y, direction, (depth -1), choices, copiedCards);

            if (result[0] < best[0]) {
                best[0] += result[0];
//                best[0] = result[0];

                for (int i = 0; i < depth; i++) {
                    best[9-i] = result[9-i];
                }
            }
        }
        System.out.println(", best: "+best[0]+", with depth: "+depth);
        return best;
    }

    private static int calculateDistance(int playerX, int playerY, int flagX, int flagY) {
        return (int) Math.sqrt((flagY - playerY) * (flagY - playerY) + (flagX - playerX) * (flagX - playerX));
    }

    public int[] applyCardProperties(int cardType, int x, int y, int direction) {
        switch (cardType) {
            case 0:
                return moveByDirection(x,y, 1, direction);
            case 2:
                return moveByDirection(x,y, 2, direction);
            case 1:
                return moveByDirection(x,y, 3, direction);
            case 3:
                return backUp(x, y, direction);
            case 4:
                return new int[]{x, y, rotate(3, direction)};
            case 5:
                return new int[]{x, y, rotate(1, direction)};
            case 6:
                return new int[]{x, y, rotate(2, direction)};
            default:
                throw new Error("fail at applyCardProperties(), with x: "+x+", y: "+y+", dir: "+direction);
        }
    }

    public int[] backUp(int x, int y, int direction){
        switch(direction) {
            case 0:
                return new int[]{x, y-1, direction};
            case 1:
                return new int[]{x-1, y, direction};
            case 2:
                return new int[]{x, y+1, direction};
            case 3:
                return new int[]{x+1, y, direction};
            default:
                throw new Error("fail at backUp(), with x: "+x+", y: "+y+", dir: "+direction);
        }
    }

    public int[] moveByDirection(int x, int y, int amountToMove, int direction){
        switch(direction) {
            case 0:
                return new int[]{x, y+amountToMove, direction};
            case 1:
                return new int[]{x+amountToMove, y, direction};
            case 2:
                return new int[]{x, y-amountToMove, direction};
            case 3:
                return new int[]{x-amountToMove, y, direction};
            default:
                throw new Error("fail at moveByDirection(), with x: "+x+", y: "+y+", dir: "+direction);
        }
    }

    public int rotate(int amountToRotate, int direction) {
        return (direction + amountToRotate) % 4;
    }

    public boolean canMove(int x, int y, int dir) {
        int dx = 0;
        int dy = 0;
        switch (dir) {
            case 0: dy++; break;
            case 1: dx++; break;
            case 2: dy--; break;
            case 3: dx--; break;
        }
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

    public int numberOfFlagsReached() {
        if (flagsReached[2]) return 3;
        if (flagsReached[1]) return 2;
        if (flagsReached[0]) return 1;
        else return 0;
    }

    public void chooseCardsFromDealtProgramCards() {
        int numCardsAllowed = Math.min(9-dmgTokens, 5);
        chosenProgramCards = new ArrayList<>();
        for (int i = 0; i < numCardsAllowed; i++) {
            chosenProgramCards.add(dealtProgramCards.get(i));
        }
    }

}
