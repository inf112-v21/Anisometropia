package multiplayer;

import p2p.Multiplayer;

import java.io.IOException;
/*
NOTE: Will try to make junit tests for this in future if possible, but for now it's
a manual test because it requires two separate program instances to be ran concurrently
and inspect the order in which data is transferred in real time.

***Running Instructions***
Run Server, then run client.

***Expected outcome***
Both instances of the program should automatically establish a connection and
exchange the pre-programmed messages.

 */
public class Server {
    public static void main(String[] args) throws IOException {
        Multiplayer mp = new Multiplayer(Boolean.TRUE);
        System.out.println(mp.receive());
        mp.send("server says hi");
        System.out.println(mp.receive());
        mp.disconnect();
    }
}
