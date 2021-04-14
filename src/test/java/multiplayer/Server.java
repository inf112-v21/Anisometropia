package multiplayer;

import com.badlogic.gdx.Game;
import launcher.GameApplication;
import p2p.Multiplayer;
import screens.OnNetSetupScreen;

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
//public class Server {
//    public static void main(String[] args) throws IOException {
//        Multiplayer r1=new Multiplayer(Boolean.TRUE);
//        Thread t1 =new Thread(r1);
//        t1.start();
//        while (r1.isConnected() == false) {
//        }
//        System.out.println(r1.receive());
//        r1.send("server says hi");
//        System.out.println(r1.receive());
//        r1.disconnect();
//    }
//}
