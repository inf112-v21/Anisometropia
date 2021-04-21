package p2p;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PeerHandler implements Runnable {
    private static Set<String> names = new HashSet<>();
    private static Set<PrintWriter> writers = new HashSet<>();

    private String name;
    private Socket socket;
    private Scanner in;
    private PrintWriter out;

    public PeerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                out.println("SUBMITNAME");
                name = in.nextLine();
                if (name == null) {
                    return;
                }
                synchronized (names) {
                    if (!name.equals("") && !names.contains(name)) {
                        names.add(name);
                        break;
                    }
                }
            }

            out.println("NAMEACCEPTED " + name);
            for (PrintWriter writer : writers) {
                writer.println("MESSAGE " + name + " has joined");
            }
            writers.add(out);

            while (true) {
                out.println("SUBMITPOSITION");
                String inputted = in.nextLine();
                if (inputted == null) {
                    return;
                }
//                try {
//                    String[] positionString = input.split(" ");
//                    int[] position = new int[2];
//                    position[0] = Integer.parseInt(positionString[0]);
//                    position[1] = Integer.parseInt(positionString[1]);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                synchronized (names) {
//                    if (!name.isBlank() && !names.contains(name)) {
//                        names.add(name);
//                        break;
//                    }
//                }
                for (PrintWriter writer : writers) {
                    writer.println("POSITION " + name + " " + inputted);
                }
                break;
            }

            while (true) {
                String input = in.nextLine();
                if (input.toLowerCase().startsWith("/quit")) {
                    return;
                }
                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + name + ": " + input);
                }
            }
        } catch (Exception e) {
            System.out.println("Client not found: "+e);
        } finally {
            if (out != null) {
                writers.remove(out);
            }
            if (name != null) {
                System.out.println(name + " is leaving");
                names.remove(name);
                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + name + " has left");
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}