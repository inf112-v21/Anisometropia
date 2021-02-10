package multiplayer;

import multiplayer.Main2;
import multiplayer.Message;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler extends Listener {
    static Client client;
    static int udpPort = 54555, tcpPort = 54777;
    public static String ip = "127.0.0.1";
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    public static void start() {
        try {
            client = new Client();
            Register.register(client.getKryo());
            client.start();
            client.connect(5000, ip, tcpPort, udpPort);
            client.addListener(new ClientHandler());
            Main2.g.taChat.append("[" + LocalTime.now().format(dtf) + " Connected as Client]\n");
            Main2.isConnected = true;
            Main2.g.btnHost.setEnabled(false);
            Main2.g.btnClient.setEnabled(false);
            Main2.g.btnDisconnect.setEnabled(true);
        } catch (IOException e) {
            Main2.g.taChat.append("[" + LocalTime.now().format(dtf) + " No Host found]\n");
            Main2.g.btnHost.setEnabled(true);
            Main2.g.btnClient.setEnabled(true);
            Main2.g.btnDisconnect.setEnabled(false);
            e.printStackTrace();

        }
    }

    public static void send(Object o) {
        if (o instanceof Message) {
            Message m = (Message) o;
            Main2.g.taChat.append(m.getMessage());
            client.sendTCP(m);
        }
    }

    public void received(Connection c, Object o){
        if(o instanceof Message){
            Message m = (Message) o;
            Main2.g.taChat.append(m.getMessage());
        }
    }

    public static void close() {
        Main2.g.taChat.append("[Disconnected]\n");
        client.close();
    }

}
