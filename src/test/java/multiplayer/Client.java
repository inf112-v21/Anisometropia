package multiplayer;

import p2p.Multiplayer;

import java.io.IOException;
// See Server class for test documentation.

public class Client {
    public static void main(String[] args) throws IOException {
        Multiplayer r1=new Multiplayer(Boolean.FALSE);
        Thread t1 =new Thread(r1);
        t1.start();
        while (r1.isConnected() == false) {
        }
        r1.send("client says hi");
        String trick = r1.receive();
        System.out.println(trick);
        r1.send("you just said " + trick);
        r1.disconnect();
    }
}
