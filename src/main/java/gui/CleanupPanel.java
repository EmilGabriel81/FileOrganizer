package gui;

import files.FileScanner;
import files.FileOrganizer;
import files.FileMover;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class CleanupPanel extends JPanel {

    private LogPanel logPanel;

    public CleanupPanel(LogPanel logPanel) {
        this.logPanel = logPanel;
        setLayout(new BorderLayout());

        JButton runCleanup = new JButton("Run Cleanup");
        JTextArea output = new JTextArea();
        output.setEditable(false);

        runCleanup.addActionListener(e -> {

            output.append("Scanning Downloads...\n");

            List<File> files = FileScanner.scanDownloads();

            for (File f : files) {
                String category = FileOrganizer.getCategoryForFile(f);
                boolean moved = FileMover.moveFile(f, category);
                logPanel.appendLog(f.getName() + " → " + category + " : " + (moved ? "OK" : "FAILED") + "\n");
            }

            output.append("Cleanup completed.\n");
        });

        add(runCleanup, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
    }
}