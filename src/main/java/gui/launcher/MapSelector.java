package gui.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.MainFrame;
import model.level.MapModel;
import util.Resource;

public class MapSelector extends JPanel {
    private final JComboBox<String> mapBox;
    private final JLabel selectedLabel;
    private File loadedMap;

    public MapSelector() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.weighty = 1;

        // Labels (col 0)
        constraints.gridx = 0;
        constraints.weightx = 1;

        constraints.gridy = 0;
        add(new JLabel("Default maps:"), constraints);

        constraints.gridy = 1;
        add(new JLabel("Load custom map:"), constraints);

        // Default map selector (row 0, col 1-2)
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        InputStream in = MapSelector.class.getResourceAsStream("mapIndex");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        mapBox = new JComboBox<>(reader.lines().toArray(String[]::new));
        add(mapBox, constraints);

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridwidth = 1;

        // Custom loading buttons (row 1, cols 1/2)
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.ipadx = 80;
        constraints.ipady = 25;

        constraints.gridx = 1;
        JButton open = new JButton("Open");
        open.addActionListener(e -> open());
        add(open, constraints);

        constraints.gridx = 2;
        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> clear());
        add(clear, constraints);

        // Loaded file label (row 2, cols 1-2)
        constraints.gridy = 2;
        constraints.gridx = 1;
        constraints.gridwidth = 2;

        selectedLabel = new JLabel("No map selected");
        add(selectedLabel, constraints);
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
        mapBox.setEnabled(false);
        selectedLabel.setText("Loaded map \"" + loadedMap.getName() + "\"");
    }

    private void clear() {
        loadedMap = null;
        mapBox.setEnabled(true);
        selectedLabel.setText("No map selected");
    }

    public Resource getSelectedMap() {
        if (loadedMap != null) {
            return new Resource(loadedMap);
        }
        String path = "maps/" + mapBox.getSelectedItem();
        return new Resource(path, MapModel.class::getResourceAsStream);
    }
}
