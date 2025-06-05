package case_study11;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatServer {
    private static final int PORT = 1234;
    private static Set<ClientHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>());
    private static Map<String, ClientHandler> users = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        System.out.println("Server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket);
                clientHandlers.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message) {
        synchronized (clientHandlers) {
            for (ClientHandler ch : clientHandlers) {
                ch.sendMessage("MESSAGE " + message);
            }
        }
    }

    public static void updateUserList() {
        StringBuilder sb = new StringBuilder("USERLIST");
        synchronized (users) {
            for (String user : users.keySet()) {
                sb.append(" ").append(user);
            }
        }
        synchronized (clientHandlers) {
            for (ClientHandler ch : clientHandlers) {
                ch.sendMessage(sb.toString());
            }
        }
    }

    public static void removeClient(ClientHandler ch) {
        clientHandlers.remove(ch);
        users.values().remove(ch);
        updateUserList();
    }

    public static boolean addUser(String username, ClientHandler ch) {
        synchronized (users) {
            if (users.containsKey(username)) return false;
            users.put(username, ch);
            updateUserList();
            return true;
        }
    }
}
