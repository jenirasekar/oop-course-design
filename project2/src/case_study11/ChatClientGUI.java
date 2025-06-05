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
    private DefaultListModel<String> userListModel = new DefaultListModel<>();
    private JList<String> userList = new JList<>(userListModel);

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

        frame = new JFrame("Chat room - " + nickname);
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        userList.setBorder(BorderFactory.createTitledBorder("Online Users"));
        userList.setPreferredSize(new Dimension(150, 0));

        frame.getContentPane().add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(userList), BorderLayout.EAST);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        client.receiveMessages(
                message -> SwingUtilities.invokeLater(() -> chatArea.append(message + "\n")),
                usernames -> SwingUtilities.invokeLater(() -> {
                    userListModel.clear();
                    for (String name : usernames) {
                        userListModel.addElement(name);
                    }
                })
        );

        ActionListener sendAction = e -> {
            String msg = inputField.getText().trim();
            if (!msg.isEmpty()) {
                String timestamp = getTimeStamp();
                client.sendMessage("[" + timestamp + "] " + msg);
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
