package p2p;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Multiplayer implements Runnable {

    private ServerSocket ss;
    private Socket sock;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private String buffer = ""; // to store the received string
    public Boolean hosting;
    private Boolean connected = false;

    /**
    @param host True if hosting, false if joining an already opened connection.
     */
    public Multiplayer(Boolean host) throws IOException {
        hosting = host;
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
        dataOutput.writeUTF(msg);
        dataOutput.flush();
    }

    /**
     * Actively listens for another machine to send a message.
     * Only receives 1 message (string), to receive multiple messages,
     * call again or create a loop.
     * @return returns the received string
     * @throws IOException
     */
    public String receive() throws IOException {
        String temp;
        while (buffer.equals("")) {
            buffer = dataInput.readUTF();
        }
        temp = buffer;
        buffer = "";
        return temp;
    }

    public Boolean isConnected() { return connected; }

    public Boolean isHosting() { return hosting; }

    @Override
    public void run() {
        if (hosting) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataOutput = new DataOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        connected = true;
    }
}
