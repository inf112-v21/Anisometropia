package inf112.skeleton.app;

import com.esotericsoftware.kryonet.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Multiplayer {

    private ServerSocket ss;
    private Socket sock;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    private String buffer = "";

    /**
    Calling the constructor establishes the connection.
    @param host True if hosting, false if joining an already opened connection.
     */
    public Multiplayer(Boolean host) throws IOException {

        if (host) {
            ss = new ServerSocket(6969);
            sock = ss.accept();
        }
        else {
            sock = new Socket("localhost", 6969);
        }
        dataInput = new DataInputStream(sock.getInputStream());
        dataOutput = new DataOutputStream(sock.getOutputStream());

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

    public void send(String msg) throws IOException {
        dataOutput.writeUTF(msg);
        dataOutput.flush();
    }

    public String receive() throws IOException {
        String temp;
        while (buffer == "") {
            buffer = dataInput.readUTF();
        }
        temp = buffer;
        buffer = "";
        return temp;
    }
}
