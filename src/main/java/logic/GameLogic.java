package logic;

import actor.Player;
import assets.ConveyorBelts;
import assets.Laser;
import assets.Wall;
import cards.DeckOfProgramCards;
import cards.ProgramCard;
import map.GameMap;
import p2p.Multiplayer;

import java.io.IOException;
import java.util.ArrayList;

public class GameLogic {

    GameMap gameMap;
    PlayerQueue playerQueue;
    ConveyorBelts conveyorBelts;
    Laser laser;
    Wall wall;
    DeckOfProgramCards deckOfProgramCards;
    public Multiplayer mp;
    public Boolean firstTurn = true;

    final int FLAG_1_ID = 55, FLAG_2_ID = 63, FLAG_3_ID = 71, FLAG_4_ID = 79;
    final int StartPosID_1 = 121, StartPosID_2 = 122, StartPosID_3 = 123, StartPosID_4 = 124, StartPosID_5 = 125, StartPosID_6 = 126, StartPosID_7 = 127, StartPosID_8 = 128;

    public static boolean gameOver = false;
    public static boolean cardExecutionInProgress = false;

    int currentCardExecutionNumber = 0;

    public static String gameMessage;

    public GameLogic(GameMap gameMap, PlayerQueue playerQueue) {
        this.gameMap = gameMap;
        this.playerQueue = playerQueue;
        conveyorBelts = new ConveyorBelts();
        laser = new Laser();
        deckOfProgramCards = new DeckOfProgramCards();
        dealProgramCards();
//        playerStartPos();
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
        if(mp != null) {
            if (firstTurn == false) {
                receiveCards();
            }
            getCurrentPlayer().setChosenProgramCards(chosenCards);
            System.out.println(getPlayerQueue().turnCounter);
            sendCards();

            // for(Player player : playerQueue.getPlayerQueue()
        }
        else {
            getCurrentPlayer().setChosenProgramCards(chosenCards);
            System.out.println(getPlayerQueue().turnCounter);
        }
    }

    public void sendCards() throws IOException {
        firstTurn = false;
        DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
        String toSend = "";
        for(int i = 0; i <= 6; i++) {
            for(ProgramCard playerCard : getCurrentPlayer().getChosenProgramCards()) {
                if(deckOfProgramCards.uniqueCards.get(i).getCardType() == playerCard.getCardType()) {
                    System.out.println(deckOfProgramCards.uniqueCards.get(i).amountToMoveOrRotate);
                    toSend += playerCard.getCardType();
                }
            }
        }
        System.out.println("I am sending " + toSend);
        mp.send(toSend);
    }

    private void receiveCards() throws IOException {
        DeckOfProgramCards deckOfProgramCards = new DeckOfProgramCards();
        String toReceive = mp.receive();
        ArrayList<ProgramCard> chosenCards = new ArrayList<>();
        for (int i = 0; i < toReceive.length(); i++) {
            int cardID = Character.getNumericValue(toReceive.charAt(i));
            System.out.println(cardID);
            chosenCards.add(deckOfProgramCards.uniqueCards.get(cardID));
        }
        getCurrentPlayer().setChosenProgramCards(chosenCards);
        System.out.println("I am receiving " + chosenCards);
        getPlayerQueue().next();
    }

    /**
     * Executes chosen cards for players, one card at a time (and updates counter)
     */
    public void executeCard() {
        if (!getCurrentPlayer().isDead) {
            getCurrentPlayer().getChosenProgramCards().get(currentCardExecutionNumber).executeProgram(getCurrentPlayer());
        }
//        endOfTurnCheck();
        if (getCurrentPlayer() == getLastPlayer()) {
            if (currentCardExecutionNumber == 4) {
                currentCardExecutionNumber = 0;
                cardExecutionInProgress = false;
                endOfRoundCheck();
                dealProgramCards();
            } else {
                currentCardExecutionNumber++;
                endOfTurnCheck();
            }
        }
        playerQueue.next();
    }

    public void endOfTurnCheck() {
        for (Player player : playerQueue.getPlayerQueue()) {
            conveyorBelts.isPlayerOnConveyorBelt(player, gameMap);
            laser.isPlayerHitByLaserBeam(player, gameMap);

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

            if(getCurrentPlayer().playerPoweredDown) {
                getCurrentPlayer().playerPoweredDown = false;
            }
        }
    }

    public void endOfRoundCheck() {
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
            gameMessage = winningPlayer.playerName + " is the only survivor!";
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
        // TODO player can not jump over a hole.
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