package gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatOneDarkIJTheme;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Downloads Organizer - Java Edition");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("TabbedPane.tabSeparatorsFullHeight", true);
        UIManager.put("TabbedPane.tabsOverlapBorder", true);

        JTabbedPane tabs = new JTabbedPane();
        LogPanel logPanel = new LogPanel();
        CleanupPanel cleanupPanel = new CleanupPanel(logPanel);

        tabs.addTab("Extensions", new ExtensionManagerPanel());
        tabs.addTab("Cleanup", cleanupPanel);
        tabs.addTab("Logs", logPanel);

        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {

        FlatOneDarkIJTheme.setup();

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);

        });
    }
}
