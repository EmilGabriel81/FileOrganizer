package gui;

import config.ConfigLoader;
import config.ConfigModel;
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
    private ConfigModel config;

    public CleanupPanel(LogPanel logPanel) {
        this.logPanel = logPanel;
        this.config= ConfigLoader.loadConfig();

        setLayout(new BorderLayout());

        JButton runCleanup = new JButton("Run Cleanup");
        JButton previewCleanup = new JButton("Preview Cleanup");
        JButton undoCleanup = new JButton("Undo Last Cleanup");

        JTextArea output = new JTextArea();
        output.setEditable(false);

        previewCleanup.addActionListener(e -> {

            output.append("Previewing...\n");

            File cleanupFolder = new File(config.getCleanupFolder());
            File[] files = cleanupFolder.listFiles();

            if (files == null) {
                output.append("Folder invalid.\n");
                return;
            }

            for (File file : files) {
                if (!file.isFile()) continue;

                String category = FileOrganizer.getCategoryForFile(file);
                logPanel.appendLog(file.getName() + " → " + category + " (preview)\n");
            }

            output.append("Preview completed.\n");
        });

        runCleanup.addActionListener(e -> {

            output.append("Scanning Downloads...\n");

            File cleanupFolder = new File(config.getCleanupFolder());
            File[] files = cleanupFolder.listFiles();

            if (files == null) {
                output.append("Folder invalid.\n");
                return;
            }

            for (File file : files) {

                if (!file.isFile()) continue;

                if (file.length() > config.getIgnoreLargerThanMB() * 1024L * 1024L) {
                    continue;
                }

                long hours = config.getIgnoreRecentHours();
                if (hours > 0) {
                    long cutoff = System.currentTimeMillis() - hours * 3600 * 1000;
                    if (file.lastModified() > cutoff) {
                        continue;
                    }
                }

                String category = FileOrganizer.getCategoryForFile(file);
                boolean moved = FileMover.moveFile(file, category);

                logPanel.appendLog(file.getName() + " → " + category + " : " + (moved ? "OK" : "FAILED") + "\n");
            }

            output.append("Cleanup completed.\n");
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

                logPanel.appendLog(
                        moved.getName() + " ← restored to " + original.getParent() + " : " + (ok ? "OK" : "FAILED") + "\n"
                );
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