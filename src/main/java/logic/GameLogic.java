package logic;

import actor.Player;
import assets.Laser;
import cards.DeckOfRegisterCards;
import cards.RegisterCard;
import map.GameMap;
import assets.ConveyorBelts;
import p2p.Multiplayer;

import java.util.ArrayList;


public class GameLogic {
    // comment
    GameMap gameMap;
    PlayerQueue playerQueue;
    ConveyorBelts conveyorBelts;
    Laser laser;
    public Multiplayer mp;

    final int FLAG_1_ID = 55, FLAG_2_ID = 63, FLAG_3_ID = 71, FLAG_4_ID = 79;
    final int StartPosID_1 = 121, StartPosID_2 = 122, StartPosID_3 = 123, StartPosID_4 = 124, StartPosID_5 = 125, StartPosID_6 = 126, StartPosID_7 = 127, StartPosID_8 = 128;

    public static boolean gameOver = false;
    public static boolean cardExecutionInProgress = false;

    int currentCardExecutionNumber = 0;

    public static String gameMessage;

    public GameLogic(GameMap gameMap) {
        this.gameMap = gameMap;
        playerQueue = new PlayerQueue();
        conveyorBelts = new ConveyorBelts();
        laser = new Laser();

        addPlayer(2,2, "player 1");
        addPlayer(3, 2, "player 2");
//        playerStartPos();
        dealRegisterCards();
    }

    public void addPlayer(int spawnX, int spawnY, String name) {
        playerQueue.add(new Player(spawnX, spawnY, name, gameMap));
        gameMap.setPlayerPosition(spawnX, spawnY, playerQueue.getPlayerQueue().get(playerQueue.getPlayerQueue().size() - 1));
    }

//    public void playerStartPos() {
//        for (int height = 0; height < gameMap.getHeight(); height++) {
//            for (int width = 0; width < gameMap.getWidth(); width++) {
//                if (gameMap.getAssetLayerID(height, width) == (startPosID_3)) {
//                    int height3 = height;
//                    int width3 = width;
//                    addPlayer(height3,width3,"player3");
//                }
//                if (gameMap.getAssetLayerID(height, width) == (startPosID_4) ) {
//                    addPlayer(height, width, "Player4"); }
//            }
//        }
//    }

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

    public void dealRegisterCards() {
        DeckOfRegisterCards deckOfRegisterCards = new DeckOfRegisterCards();
        for (Player player : playerQueue.getPlayerQueue()) {
            player.setDealtRegisterCards(deckOfRegisterCards.dealNineCards());
        }
    }


    /**
     * Saves player's chosen cards and ends the turn.
     * @param chosenCards
     */
    public void finishTurn(ArrayList<RegisterCard> chosenCards) {
        getCurrentPlayer().setChosenRegisterCards(chosenCards);
    }

    /**
     * Executes chosen cards for players, one card at a time (and updates counter)
     */
    public void executeCard() {
        if (!getCurrentPlayer().isDead) {
            getCurrentPlayer().getChosenRegisterCards().get(currentCardExecutionNumber).executeRegister(getCurrentPlayer());
        }
        endOfTurnCheck();
        if (getCurrentPlayer() == getLastPlayer()) {
            if (currentCardExecutionNumber == 4) {
                currentCardExecutionNumber = 0;
                cardExecutionInProgress = false;
                endOfRoundCheck();
                dealRegisterCards();
            } else {
                currentCardExecutionNumber++;
            }
        }
        playerQueue.next();
    }

    public void endOfTurnCheck() {
        for (Player player : playerQueue.getPlayerQueue()) {
            if (checkLoss(player.getX(), player.getY())) {
                if(!player.isDead) {
                    player.playerDies();
                    gameMap.setPlayerPosition(player.getX(), player.getY(), player);
                }
            }
            if (checkWin(player)) {
                player.playerWins();
                gameMap.setPlayerPosition(player.getX(), player.getY(), player);
                gameMessage = player.playerName + " won the game!";
                gameOver = true;
            }
        }
    }

    public void endOfRoundCheck() {
        for (Player player : playerQueue.getPlayerQueue()) {
            conveyorBelts.isPlayerOnConveyorBelt(player, gameMap);
            laser.isPlayerHitByLaserBeam(player, gameMap);
        }
        endOfTurnCheck();
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
            gameMessage = winningPlayer.playerName + " won the game!";
            gameOver = true;
        }
    }

    private void respawnPlayersIfPossible() {
        for (Player player : playerQueue.getPlayerQueue()) {
            if(player.isDead) player.respawn();
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