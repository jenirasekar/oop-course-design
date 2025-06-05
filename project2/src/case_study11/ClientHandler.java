package case_study11;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private static Set<PrintWriter> writers = Collections.synchronizedSet(new HashSet<>());
    private static Map<String, PrintWriter> users = Collections.synchronizedMap(new HashMap<>());
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Minta nickname
            out.println("SUBMITNAME");
            String nameMsg = in.readLine();
            if (nameMsg != null && nameMsg.startsWith("NAME:")) {
                username = nameMsg.substring(5).trim();
            } else {
                out.println("INVALIDNAME");
                socket.close();
                return;
            }

            synchronized (users) {
                if (users.containsKey(username)) {
                    out.println("NAMEEXISTS");
                    socket.close();
                    return;
                }
                users.put(username, out);
            }

            synchronized (writers) {
                writers.add(out);
            }

            broadcast("[SERVER] " + username + " has joined the chat.");

            String msg;
            while ((msg = in.readLine()) != null) {
                broadcast(msg);
            }

        } catch (IOException e) {
            System.out.println("Client disconnected: " + username);
        } finally {
            try {
                socket.close();
                synchronized (writers) {
                    writers.remove(out);
                }
                synchronized (users) {
                    users.remove(username);
                }
                broadcast("[SERVER] " + username + " has left the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(String message) {
        synchronized (writers) {
            for (PrintWriter writer : writers) {
                writer.println("MESSAGE " + message);
            }
        }
    }
}
