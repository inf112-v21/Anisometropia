package p2p;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        Multiplayer mp = new Multiplayer(Boolean.TRUE);
        System.out.println(mp.receive());
        mp.send("server says hi");
        System.out.println(mp.receive());
        mp.disconnect();
    }
}
