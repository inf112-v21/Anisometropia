package p2p;

import screens.OnNetSetupScreen;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Multiplayer implements Runnable {

    private ServerSocket ss;
    private Socket sock;
    private DataInputStream dataInput;
    public Boolean hosting;
    public Boolean connected = false;

    Scanner in;
    PrintWriter dataOutput;
    private String toSend = "";

    private OnNetSetupScreen onNetSetupScreen;

    /**
    @param host True if hosting, false if joining an already opened connection.
     */
    public Multiplayer(Boolean host, OnNetSetupScreen onNetSetupScreen) {
        hosting = host;
        this.onNetSetupScreen = onNetSetupScreen;
    }

    /**
     * Call me when the game is finished
     * @throws IOException e
     */
    public void disconnect() throws IOException {
        // TODO: Server socket should also be closed for the server!
        if(hosting && ss != null) ss.close();
        if(sock != null) sock.close();
        dataInput.close();
        dataOutput.close();
    }

    /**
     * @param msg Message to send as a string
     * @throws IOException e
     */
    public void send(String msg) throws IOException {
        dataOutput.println(msg);
    }

    /**
     * Actively listens for another machine to send a message.
     * Only receives 1 message (string), to receive multiple messages,
     * call again or create a loop.
     * @throws IOException e
     */
    public void receive() throws IOException {
        if(dataInput.available() > 0) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println("RECEIVED: "+line);
                handleReceivedMessage(line);
                if(dataInput.available() == 0) break;
            }
        }
    }

    public Boolean isConnected() { return connected; }

    public Boolean isHosting() { return hosting; }

    @Override
    public void run() {
        if (hosting) {
            OnNetSetupScreen.numPlayers = 1;
            onNetSetupScreen.addUnreadyPlayerToPlayerReadyList();
            try {
                ss = new ServerSocket(onNetSetupScreen.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sock = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                sock = new Socket(onNetSetupScreen.getIPAddress(), onNetSetupScreen.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            dataInput = new DataInputStream(sock.getInputStream());
            in = new Scanner(dataInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutput = new PrintWriter(sock.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = true;

        while (connected) {
            try {
                receive();
                if (!toSend.equals("")) {
                    System.out.println("SENDING: "+toSend);
                    send(toSend);
                    toSend = "";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setToSend(String message) {
        toSend = message;
    }

    private void handleReceivedMessage(String received) throws IOException {
        String[] splitReceived = received.split(" ");
        String firstWord = splitReceived[0];
        switch (firstWord) {
            case "START":
                send("MAP_UPDATE_BEFORE_START_REQUEST");
                break;
            case "MAP_UPDATE_BEFORE_START":
                onNetSetupScreen.setCurrentMap(splitReceived[1]);
                send("AMOUNT_PLAYERS_BEFORE_START_REQUEST");
                break;
            case "MAP_UPDATE_BEFORE_START_REQUEST":
                send("MAP_UPDATE_BEFORE_START "+onNetSetupScreen.getCurrentMap());
                break;
            case "AMOUNT_PLAYERS_BEFORE_START":
                OnNetSetupScreen.numPlayers = Integer.parseInt(splitReceived[1]);
                while (onNetSetupScreen.getPlayersReady().size() < (OnNetSetupScreen.numPlayers)) {
                    onNetSetupScreen.addUnreadyPlayerToPlayerReadyList();
                }
                send("WHAT_IS_YOUR_NAME");
                OnNetSetupScreen.canStart = true;
                break;
            case "AMOUNT_PLAYERS_BEFORE_START_REQUEST":
                send("AMOUNT_PLAYERS_BEFORE_START "+OnNetSetupScreen.numPlayers);
                break;
            case "WHAT_IS_YOUR_NAME":
                send("MY_NAME_IS "+OnNetSetupScreen.playerID+" "+onNetSetupScreen.getPlayerName());
                break;
            case "MY_NAME_IS":
                onNetSetupScreen.playerNames.put(Integer.parseInt(splitReceived[1]), splitReceived[2]);
                break;
            case "INITIAL_ID":
                OnNetSetupScreen.playerID = Integer.parseInt(splitReceived[1]);
                send("MY_NAME_IS "+OnNetSetupScreen.playerID+" "+onNetSetupScreen.getPlayerName());
                break;
            case "INITIAL_ID_REQUEST":
                send("INITIAL_ID "+OnNetSetupScreen.numPlayers);
                OnNetSetupScreen.numPlayers++;
                onNetSetupScreen.addUnreadyPlayerToPlayerReadyList();
                break;
            case "CARD":
                onNetSetupScreen.getMultiplayerLogic().receiveCards(splitReceived);
                onNetSetupScreen.setPlayersReady(Integer.parseInt(splitReceived[1]), true);
                break;
            case "POWER_DOWN":
                onNetSetupScreen.getMultiplayerLogic().receivePowerDown(splitReceived);
                break;
            case "OPTION_CARD":
                onNetSetupScreen.getMultiplayerLogic().receiveOptionCard(splitReceived);
                break;
            default:
                System.out.println("(!!!) unrecognized message: "+received);
        }
    }
}
