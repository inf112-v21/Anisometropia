package p2p;

import java.io.IOException;

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
