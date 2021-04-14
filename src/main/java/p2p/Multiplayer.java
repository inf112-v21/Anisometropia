package p2p;

import screens.OnNetSetupScreen;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Multiplayer implements Runnable {

    private ServerSocket ss;
    private Socket sock;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private String buffer = ""; // to store the received string
    public Boolean hosting;
    private Boolean connected = false;

    Scanner in;
    PrintWriter out;
    private String toSend = "";

    private OnNetSetupScreen onNetSetupScreen;

    /**
    @param host True if hosting, false if joining an already opened connection.
     */
    public Multiplayer(Boolean host, OnNetSetupScreen onNetSetupScreen) throws IOException {
        hosting = host;
        this.onNetSetupScreen = onNetSetupScreen;
    }

    /**
     * Call me when the game is finished
     * @throws IOException
     */
    public void disconnect() throws IOException {
        // TODO: Server socket should also be closed for the server!
        sock.close();
        dataInput.close();
        dataOutput.close();
    }

    /**
     * @param msg Message to send as a string
     * @throws IOException
     */
    public void send(String msg) throws IOException {
//        dataOutput.writeUTF(msg);
//        dataOutput.flush();
        out.println(msg);
    }

    /**
     * Actively listens for another machine to send a message.
     * Only receives 1 message (string), to receive multiple messages,
     * call again or create a loop.
     * @return returns the received string
     * @throws IOException
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
            onNetSetupScreen.getMultiplayerLogic().playersReady.add(true);
            try {
                ss = new ServerSocket(6969);
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
                sock = new Socket("localhost", 6969);
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
//            dataOutput = new DataOutputStream(sock.getOutputStream());
            out = new PrintWriter(sock.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = true;

        while (true) {
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
                OnNetSetupScreen.canStart = true;
                break;
            case "ID":
                OnNetSetupScreen.playerID = Integer.parseInt(splitReceived[1]);
                break;
            case "ID_REQUEST":
                send("ID "+OnNetSetupScreen.numPlayers);
                OnNetSetupScreen.numPlayers++;
                break;
            case "AMOUNT_PLAYERS":
                OnNetSetupScreen.numPlayers = Integer.parseInt(splitReceived[1]);
                while(onNetSetupScreen.getMultiplayerLogic().playersReady.size() < Integer.parseInt(splitReceived[1])){
                    onNetSetupScreen.addToPlayersReady();
                }
                break;
            case "AMOUNT_PLAYERS_REQUEST":
                send("AMOUNT_PLAYERS "+OnNetSetupScreen.numPlayers);
                break;
            case "CARD":
                onNetSetupScreen.getMultiplayerLogic().receiveCards(splitReceived);
                onNetSetupScreen.setPlayersReady(Integer.parseInt(splitReceived[1]), true);
                break;
            default:
                System.out.println("(!!!) unrecognized message: "+received);
        }
    }
}
