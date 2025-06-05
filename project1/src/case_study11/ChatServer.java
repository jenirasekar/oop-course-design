package case_study11;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 1234;
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Chat server started...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected: " + client);
                new ClientHandler(client, clientWriters).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
