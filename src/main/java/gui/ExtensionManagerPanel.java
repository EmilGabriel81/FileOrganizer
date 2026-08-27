package gui;

import config.ConfigLoader;
import config.ConfigModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.*;

public class ExtensionManagerPanel extends JPanel {

    private JComboBox<String> extensionDropdown;
    private JTextField customExtensionField;
    private DefaultListModel<String> extensionListModel;
    private JList<String> extensionList;

    public ExtensionManagerPanel() {

        ConfigModel config = ConfigLoader.loadConfig();

        setLayout(new BorderLayout());

        // Lista extensiilor active
        extensionListModel = new DefaultListModel<>();
        extensionList = new JList<>(extensionListModel);

        for (String ext : config.getCustomExtensions()) {
            extensionListModel.addElement(ext);
        }

        // 3. Dropdown generat din config.json
        extensionDropdown = new JComboBox<>(
                config.getRules().values()
                        .stream()
                        .flatMap(List::stream)
                        .distinct()
                        .sorted()
                        .toArray(String[]::new)
        );


        // Text field pentru extensii custom
        customExtensionField = new JTextField();
        customExtensionField.setToolTipText("Add custom extension (e.g., apk, csv, jar)");

        JButton addButton = new JButton("Add Extension");
        JButton removeButton = new JButton("Remove Selected");

        addButton.addActionListener(e -> {
            String ext = customExtensionField.getText().trim();

            if (ext.isEmpty()) {
                ext = (String) extensionDropdown.getSelectedItem();
            }

            if (!ext.isEmpty()) {
                extensionListModel.addElement(ext);
                customExtensionField.setText("");

                // Salvăm în config.json
                config.getCustomExtensions().add(ext);
                ConfigLoader.saveConfig(config);
            }
        });

        removeButton.addActionListener(e -> {
            String selected = extensionList.getSelectedValue();
            if (selected != null) {
                extensionListModel.removeElement(selected);
            }
        });

        // Layout GUI
        JPanel topPanel = new JPanel(new GridLayout(3, 1));
        topPanel.add(extensionDropdown);
        topPanel.add(customExtensionField);
        topPanel.add(addButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(extensionList), BorderLayout.CENTER);
        add(removeButton, BorderLayout.SOUTH);
    }
}
