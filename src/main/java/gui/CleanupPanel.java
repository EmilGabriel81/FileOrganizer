package gui;

import files.FileScanner;
import files.FileOrganizer;
import files.FileMover;
import files.UndoHistory;

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
        JButton previewCleanup = new JButton("Preview Cleanup");
        JButton undoCleanup = new JButton("Undo Last Cleanup");


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

        previewCleanup.addActionListener(e -> {

            output.append("Previewing Downloads...\n");

            List<File> files = FileScanner.scanDownloads();

            for (File f : files) {
                String category = FileOrganizer.getCategoryForFile(f);
                logPanel.appendLog(f.getName() + " → " + category + " (preview)");
            }

            output.append("Preview completed.\n");
        });

        undoCleanup.addActionListener(e -> {

            List<UndoHistory.MoveEntry> entries = UndoHistory.getHistory();

            if (entries.isEmpty()) {
                output.append("Nothing to undo.\n");
                return;
            }

            for (UndoHistory.MoveEntry entry : entries) {
                File original = entry.original;
                File moved = entry.moved;

                File restored = new File(original.getParent(), original.getName());

                boolean ok = moved.renameTo(restored);

                logPanel.appendLog(moved.getName() + " ← restored to " + original.getParent() + " : " + (ok ? "OK" : "FAILED"));
            }

            UndoHistory.clear();
            output.append("Undo completed.\n");
        });

        JPanel topButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topButtons.add(runCleanup);
        topButtons.add(previewCleanup);
        topButtons.add(undoCleanup);

        add(topButtons, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
    }
}