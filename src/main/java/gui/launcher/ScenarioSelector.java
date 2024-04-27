package gui.launcher;

import gui.MainFrame;
import model.level.MapModel;
import util.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ScenarioSelector extends JPanel {
    private boolean marathonMode = false;
    private final JLabel selectedLabel;
    private File loadedMap;
    private final JButton openButton;
    private final JButton clearButton;
    private final JCheckBox marathonCheckbox;
    private final JComboBox<String> defaultScenarioBox;

    public ScenarioSelector() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.weighty = 1;

        // Labels (col 0)
        constraints.gridx = 0;
        constraints.weightx = 1;

        constraints.gridy = 0;
        add(new JLabel("Default scenarios" +
                ":"), constraints);

        constraints.gridy = 1;
        add(new JLabel("Load custom scenario:"), constraints);

        // Default scenario selector (row 0, col 1-2)
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        InputStream in = ScenarioSelector.class.getResourceAsStream("scenarioIndex");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        defaultScenarioBox = new JComboBox<>(reader.lines().toArray(String[]::new));
        add(defaultScenarioBox, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridwidth = 1;

        // Custom loading buttons (row 1, cols 1/2)
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.ipadx = 80;
        constraints.ipady = 25;

        constraints.gridx = 1;
        openButton = new JButton("Open");
        openButton.addActionListener(e -> open());
        add(openButton, constraints);

        constraints.gridx = 2;
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clear());
        add(clearButton, constraints);

        constraints.gridy = 2;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        marathonCheckbox = new JCheckBox("Marathon mode (Coming soon!)", true);
        setMarathonMode(true);
        marathonCheckbox.addItemListener(e -> setMarathonMode(e.getStateChange() == ItemEvent.SELECTED));
        add(marathonCheckbox, constraints);

        // Loaded file label (row 3, cols 1-2)
        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.gridwidth = 2;

        selectedLabel = new JLabel("No scenario selected");
        add(selectedLabel, constraints);
    }

    private void setMarathonMode(boolean marathonMode) {
        this.marathonMode = marathonMode;
        if (marathonMode) {
            defaultScenarioBox.setEnabled(false);
            openButton.setEnabled(false);
            clearButton.setEnabled(false);
        } else {
            defaultScenarioBox.setEnabled(true);
            openButton.setEnabled(true);
            clearButton.setEnabled(true);
        }
    }

    private void open() {
        int returnCode = MainFrame.fileChooser.showOpenDialog(this);
        if (returnCode != JFileChooser.APPROVE_OPTION) return;

        File f = MainFrame.fileChooser.getSelectedFile();
        if (!f.exists()) {
            JOptionPane.showMessageDialog(
                this,
                "File does not exist.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        loadedMap = f;
        defaultScenarioBox.setEnabled(false);
        selectedLabel.setText("Loaded scenario \"" + loadedMap.getName() + "\"");
    }

    private void clear() {
        loadedMap = null;
        defaultScenarioBox.setEnabled(true);
        selectedLabel.setText("No map selected");
    }

    public Resource getSelectedScenario() {
        if (marathonMode) {
            return null;
        }
        if (loadedMap != null) {
            return new Resource(loadedMap);
        }
        String path = "scenario/" + defaultScenarioBox.getSelectedItem() + ".xml";

        return new Resource(path, MapModel.class::getResourceAsStream);
    }
}
