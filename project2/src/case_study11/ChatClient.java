package case_study11;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ChatClient(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void receiveMessages(MessageListener listener) {
        new Thread(() -> {
            String msg;
            try {
                while ((msg = in.readLine()) != null) {
                    listener.onMessageReceived(msg);
                }
            } catch (IOException e) {
                listener.onMessageReceived("Disconnected from server");
            }
        }).start();
    }

    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
