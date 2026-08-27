package gui;
import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {

    private JTextArea logArea;

    public LogPanel() {
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);

        JButton clearButton = new JButton("Clear Log");
        clearButton.addActionListener(e -> logArea.setText(""));

        add(new JScrollPane(logArea), BorderLayout.CENTER);
        add(clearButton, BorderLayout.SOUTH);
        add(new JScrollPane(logArea), BorderLayout.CENTER);
    }

    public void appendLog(String text) {
        logArea.append(text + "\n");
    }
}

