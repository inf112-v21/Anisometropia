package multiplayer;

import p2p.Multiplayer;

import java.io.IOException;
// See Server class for test documentation.

public class Client {
    public static void main(String[] args) throws IOException {
        Multiplayer mp = new Multiplayer(Boolean.FALSE);
        mp.send("client says hi");
        String trick = mp.receive();
        System.out.println(trick);
        mp.send("you just said " + trick);
        mp.disconnect();
    }
}
