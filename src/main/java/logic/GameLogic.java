package logic;

import actor.Player;
import assets.*;
import cards.DeckOfProgramCards;
import cards.ProgramCard;
import map.GameMap;
import screens.OnNetSetupScreen;

import java.util.ArrayList;

public class GameLogic {

    public MultiPlayerLogic multiPlayerLogic;
    GameMap gameMap;
    PlayerQueue playerQueue;
    DeckOfProgramCards deckOfProgramCards;
    // From assets
    ConveyorBelts conveyorBelts;
    Laser laser;
    Gear gear;
    Repair repair;
    Flag flag;
    Pusher pusher;

    public static boolean gameOver = false;
    public static boolean cardExecutionInProgress = false;
    public boolean lastPlayerPoweredDown = false;

    int currentCardExecutionNumber = 0;

    public static String gameMessage;

    public GameLogic(GameMap gameMap, PlayerQueue playerQueue) {
        this.gameMap = gameMap;
        this.playerQueue = playerQueue;
        conveyorBelts = new ConveyorBelts();
        laser = new Laser();
        gear = new Gear();
        repair = new Repair();
        flag = new Flag();
        pusher = new Pusher();
        deckOfProgramCards = new DeckOfProgramCards();
        dealProgramCards();
    }

    public GameLogic(GameMap gameMap, PlayerQueue playerQueue, MultiPlayerLogic multiPlayerLogic) {
        this.multiPlayerLogic = multiPlayerLogic;
        this.gameMap = gameMap;
        this.playerQueue = playerQueue;
        conveyorBelts = new ConveyorBelts();
        laser = new Laser();
        gear = new Gear();
        repair = new Repair();
        flag = new Flag();
        pusher = new Pusher();
        deckOfProgramCards = new DeckOfProgramCards();
        dealProgramCards();
    }

    public void update() {
        if (!gameOver) {
            for (Player player : getPlayerQueue().getPlayerQueue()) {
                gameMap.setPlayerPosition(player.getX(), player.getY(), player);
            }
            for (Player player : playerQueue.getPlayerQueue()) {
                flag.updateFlag(player, gameMap);
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
    public void finishCardSelectionTurn(ArrayList<ProgramCard> chosenCards) {
        if(multiPlayerLogic != null && multiPlayerLogic.isConnected()) {
            getCurrentPlayer().setChosenProgramCards(chosenCards);

            if(getCurrentPlayer().isLocal) multiPlayerLogic.sendCards();
            playerQueue.setCurrentPlayer(0);
        }
        else  {
            if(!getCurrentPlayer().isAi()) getCurrentPlayer().setChosenProgramCards(chosenCards);
        }
        for (Player player: playerQueue.getPlayerQueue()){
            player.clearPlayerShootLaserBoard();
        }
    }

    /**
     * Executes chosen cards for players, one card at a time (and updates counter)
     */
    public void executeCard() {
        if (!getCurrentPlayer().isDead || !getCurrentPlayer().isPoweredDown) {
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
            //moving assets:
            conveyorBelts.playerIsToMove(player, gameMap);
            gear.playerIsToMove(player, gameMap);
            pusher.playerIsToMove(player, gameMap);
            //repair assets:
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
        boolean localDrewOptionCard = false;
        for (Player player : playerQueue.getPlayerQueue()){
            if (player.isLocal)
                if (repair.drawOptionCardIfPossible(player, gameMap))
                    localDrewOptionCard = true;
            player.playerShootsLaser();
            player.upgradeLasers();
            player.placeLasers();
        }
        if (multiPlayerLogic != null && multiPlayerLogic.isConnected()) {
            multiPlayerLogic.setPlayersNotReady();
            if (localDrewOptionCard)
                multiPlayerLogic.sendOptionCard();
        }
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

    public void announcePowerDown() {
        getCurrentPlayer().announcePowerDown();
        if (multiPlayerLogic != null && multiPlayerLogic.isConnected()) multiPlayerLogic.sendPowerDown();
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
        return player.hasReachedAllFlags();
    }

    public boolean checkLoss(int x, int y) {
        return gameMap.isThereHoleOnThisPosition(x, y);
    }

    public Player getCurrentPlayer() { return playerQueue.getCurrentPlayer(); }

    public Player getLastPlayer() {
        return playerQueue.getLastPlayer();
    }

    public void setPlayerQueue(PlayerQueue pQueue) { playerQueue = pQueue; }

    public PlayerQueue getPlayerQueue() {
        return playerQueue;
    }

    public Player getLocalPlayer() { return playerQueue.getPlayerQueue().get(OnNetSetupScreen.playerID); }
}