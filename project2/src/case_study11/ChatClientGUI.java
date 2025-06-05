package case_study11;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatClientGUI {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private ChatClient client;
    private String nickname;

    private String getTimeStamp() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public ChatClientGUI(String serverAddress, int port) {
        nickname = JOptionPane.showInputDialog(null, "Enter your nickname:");
        if (nickname == null || nickname.trim().isEmpty()) {
            nickname = "User" + (int)(Math.random() * 1000);
        }

        try {
            client = new ChatClient(serverAddress, port);
            client.sendMessage("NAME:" + nickname);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server.");
            return;
        }

        frame = new JFrame("Chat - " + nickname);
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.getContentPane().add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        client.receiveMessages(message -> {
            if (message.startsWith("[SERVER]")) {
                chatArea.append("* " + message + "\n");
            } else {
                chatArea.append(message + "\n");
            }
        });

        ActionListener sendAction = e -> {
            String msg = inputField.getText().trim();
            if (!msg.isEmpty()) {
                String timestamp = getTimeStamp();
                client.sendMessage("[" + timestamp + "] [" + nickname + "]: " + msg);
                inputField.setText("");
            }
        };

        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatClientGUI("localhost", 1234));
    }
}
