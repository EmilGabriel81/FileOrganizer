package gui;

import config.ConfigLoader;
import config.ConfigModel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsPanel extends JPanel {

    private final ConfigModel config;

    public SettingsPanel() {
        this.config = ConfigLoader.loadConfig();
        setLayout(new BorderLayout());

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        // Folder selector
        JLabel folderLabel = new JLabel("Cleanup folder:");
        JTextField folderField = new JTextField(config.getCleanupFolder());
        JButton chooseFolder = new JButton("Browse");

        chooseFolder.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selected = chooser.getSelectedFile();
                folderField.setText(selected.getAbsolutePath());
                config.setCleanupFolder(selected.getAbsolutePath());
                ConfigLoader.saveConfig(config);
            }
        });

        // Ignore large files
        JLabel sizeLabel = new JLabel("Ignore files larger than (MB):");
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(config.getIgnoreLargerThanMB(), 0, 10000, 100));
        sizeSpinner.addChangeListener(e -> {
            config.setIgnoreLargerThanMB((Integer) sizeSpinner.getValue());
            ConfigLoader.saveConfig(config);
        });

        // Ignore recent files
        JLabel recentLabel = new JLabel("Ignore files modified in last (hours):");
        JSpinner recentSpinner = new JSpinner(new SpinnerNumberModel(config.getIgnoreRecentHours(), 0, 168, 1));
        recentSpinner.addChangeListener(e -> {
            config.setIgnoreRecentHours((Integer) recentSpinner.getValue());
            ConfigLoader.saveConfig(config);
        });

        // Theme switcher
        JLabel themeLabel = new JLabel("Theme:");
        String[] themes = {"Light", "Dark"};
        JComboBox<String> themeBox = new JComboBox<>(themes);

        themeBox.addActionListener(e -> {
            String selected = (String) themeBox.getSelectedItem();
            if ("Dark".equals(selected)) {
                FlatDarkLaf.setup();
            } else {
                FlatLightLaf.setup();
            }
            SwingUtilities.updateComponentTreeUI(SwingUtilities.getWindowAncestor(this));
        });

        // Layout
        form.add(folderLabel);
        form.add(folderField);
        form.add(chooseFolder);

        form.add(Box.createVerticalStrut(20));
        form.add(sizeLabel);
        form.add(sizeSpinner);

        form.add(Box.createVerticalStrut(20));
        form.add(recentLabel);
        form.add(recentSpinner);

        form.add(Box.createVerticalStrut(20));
        form.add(themeLabel);
        form.add(themeBox);

        add(form, BorderLayout.NORTH);
    }
}
