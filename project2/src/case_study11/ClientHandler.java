package case_study11;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("SUBMITNAME");
            while (true) {
                String line = in.readLine();
                if (line == null) return;

                if (line.startsWith("NAME:")) {
                    String requestedName = line.substring(5).trim();
                    if (requestedName.isEmpty()) {
                        out.println("INVALIDNAME");
                        continue;
                    }
                    if (ChatServer.addUser(requestedName, this)) {
                        username = requestedName;
                        out.println("NAMEACCEPTED");
                        ChatServer.broadcast("[SERVER] " + username + " has joined the chat.");
                        break;
                    } else {
                        out.println("NAMEEXISTS");
                    }
                }
            }

            String msg;
            while ((msg = in.readLine()) != null) {
                ChatServer.broadcast("[" + username + "]: " + msg);
            }

        } catch (IOException e) {
            System.out.println(username + " disconnected.");
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
            ChatServer.removeClient(this);
            ChatServer.broadcast("[SERVER] " + username + " has left the chat.");
        }
    }
}
