package case_study11;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private static Set<PrintWriter> writers;

    public ClientHandler(Socket socket, Set<PrintWriter> clientWriters) {
        this.socket = socket;
        synchronized (clientWriters) {
            writers = clientWriters;
        }
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            synchronized (writers) {
                writers.add(out);
            }

            String msg;
            while ((msg = in.readLine()) != null) {
                synchronized (writers) {
                    for (PrintWriter writer: writers) {
                        writer.println(msg);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        } finally {
            try {
                socket.close();
                synchronized (writers) {
                    writers.remove(out);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
