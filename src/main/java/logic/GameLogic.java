package logic;

import actor.Player;
import assets.ConveyorBelts;
import assets.Gear;
import assets.Laser;
import assets.Repair;
import cards.DeckOfProgramCards;
import cards.ProgramCard;
import map.GameMap;

import java.io.IOException;
import java.util.ArrayList;

public class GameLogic {

    public MultiPlayerLogic multiPlayerLogic;
    GameMap gameMap;
    PlayerQueue playerQueue;
    DeckOfProgramCards deckOfProgramCards;
    //From assets
    ConveyorBelts conveyorBelts;
    Laser laser;
    Gear gear;
    Repair repair;

    final int FLAG_1_ID = 55, FLAG_2_ID = 63, FLAG_3_ID = 71, FLAG_4_ID = 79;

    public static boolean gameOver = false;
    public static boolean cardExecutionInProgress = false;
    public boolean lastPlayerPoweredDown = false;

    int currentCardExecutionNumber = 0;

    public static String gameMessage;

    public GameLogic(GameMap gameMap, PlayerQueue playerQueue) {
        multiPlayerLogic = new MultiPlayerLogic(this);
        this.gameMap = gameMap;
        this.playerQueue = playerQueue;
        conveyorBelts = new ConveyorBelts();
        laser = new Laser();
        gear = new Gear();
        repair = new Repair();
        deckOfProgramCards = new DeckOfProgramCards();
        dealProgramCards();
    }

    public void update() {
        if (!gameOver) {
            for (Player player : getPlayerQueue().getPlayerQueue()) {
                gameMap.setPlayerPosition(player.getX(), player.getY(), player);
            }
            for (Player player : playerQueue.getPlayerQueue()) {
                if (gameMap.isThereFlagHere(player.getX(), player.getY())) {
                    int tileID = gameMap.getAssetLayerID(player.getX(), player.getY());
                    registerFlag(tileID, player);
                }
            }
        }
    }

    public void dealProgramCards() {
        deckOfProgramCards.initializeDeck();
        for (Player player : playerQueue.getPlayerQueue()) {
            player.setDealtProgramCards(deckOfProgramCards.dealCards(player));
        }
    }

    /**
     * Saves player's chosen cards and ends the turn.
     * @param chosenCards cards player has chosen.
     */
    public void finishCardSelectionTurn(ArrayList<ProgramCard> chosenCards) throws IOException {
        if(multiPlayerLogic.isConnected()) {
            System.out.println("connected");
            getCurrentPlayer().setChosenProgramCards(chosenCards);
            multiPlayerLogic.runMultiPlayer();
        }

        else  {
            getCurrentPlayer().setChosenProgramCards(chosenCards);
        }
    }

    /**
     * Executes chosen cards for players, one card at a time (and updates counter)
     */
    public void executeCard() {
        if (!getCurrentPlayer().isDead) {
            getCurrentPlayer().getChosenProgramCards().get(currentCardExecutionNumber).executeProgram(getCurrentPlayer());
        }

        boolean roundEnding = false;
        if (getCurrentPlayer() == getLastPlayer()) {
            if (currentCardExecutionNumber == 4) {
                currentCardExecutionNumber = 0;
                cardExecutionInProgress = false;
                roundEnding = true;
                endOfRoundCheck();
                dealProgramCards();
            } else {
                currentCardExecutionNumber++;
                endOfTurnCheck();
            }
        }
        // Ensures the first player's turn is not skipped if the last player powered down last round.
        if (roundEnding && lastPlayerPoweredDown && !playerQueue.getLastPlayerInQueue().isPoweredDown) {
            playerQueue.next();
            lastPlayerPoweredDown = false;
        }
        playerQueue.next();
    }

    public void endOfTurnCheck() {
        for (Player player : playerQueue.getPlayerQueue()) {
            conveyorBelts.playerIsToMove(player, gameMap);
            gear.playerIsToMove(player, gameMap);

            laser.updatePlayersHealth(player, gameMap);
            repair.updatePlayersHealth(player, gameMap);


            if (checkLoss(player.getX(), player.getY())) {
                if(!player.isDead) {
                    player.playerDies();
                    gameMap.setPlayerPosition(player.getX(), player.getY(), player);
                }
            }

            if (checkWin(player)) {
                player.playerWins();
                gameMap.setPlayerPosition(player.getX(), player.getY(), player);
                gameMessage = player.playerName + " won by finding all the flags!";
                gameOver = true;
            }
        }
    }

    public void endOfRoundCheck() {
        endOfTurnCheck();
        initiateAnnouncedPowerDowns();
        respawnPlayersIfPossible();
        checkIfOnlyOnePlayerLeft();
    }

    private void checkIfOnlyOnePlayerLeft() {
        int alivePlayers = 0;
        Player winningPlayer = null;
        for (Player player : playerQueue.getPlayerQueue()) {
            if(!player.isDead) {
                winningPlayer = player;
                alivePlayers++;
            }
        }
        if (alivePlayers == 1) {
            gameMessage = winningPlayer.playerName + " is the only survivor!";
            gameOver = true;
        }
    }

    private void initiateAnnouncedPowerDowns() {
        for (Player player : playerQueue.getPlayerQueue()) {
            player.isPoweredDown = false;
            if (player.hasAnnouncedPowerDown) {
                player.hasAnnouncedPowerDown = false;
                if (player == playerQueue.getLastPlayerInQueue()) lastPlayerPoweredDown = true;
                player.powerDownRobot();
            }
        }
    }

    private void respawnPlayersIfPossible() {
        for (Player player : playerQueue.getPlayerQueue()) {
            if(player.isDead) {
                player.respawn();
                player.isPoweredDown = false;
            }
        }
    }

    public void registerFlag (int tileID, Player player) {
        switch (tileID) {
            case (FLAG_1_ID): player.flagsReached[0] = true;
                break;
            case (FLAG_2_ID): if (player.flagsReached[0]) player.flagsReached[1] = true;
                break;
            case (FLAG_3_ID): if (player.flagsReached[1]) player.flagsReached[2] = true;
                break;
            case (FLAG_4_ID): if (player.flagsReached[2]) player.flagsReached[3] = true;
                break;
        }
    }

    public void gameReset() {
        gameOver = false;
        playerQueue.turnCounter = 0;
        deckOfProgramCards.initializeDeck();
        for (Player player : playerQueue.getPlayerQueue()){
            player.respawn();
            player.setLifeTokens(3);
            player.setDmgTokens(0);
        }
    }

    public boolean checkWin(Player player) {
        return player.hasWon();
    }

    public boolean checkLoss(int x, int y) {
        return gameMap.isThereHoleOnThisPosition(x, y);
    }

    public Player getCurrentPlayer() {
        return playerQueue.getCurrentPlayer();
    }

    public Player getLastPlayer() {
        return playerQueue.getLastPlayer();
    }

    public PlayerQueue getPlayerQueue() {
        return playerQueue;
    }

}