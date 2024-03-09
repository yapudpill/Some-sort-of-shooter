package gui.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;

public class MapMenu extends JPanel {

    public MapMenu(MainController mainController) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.weightx = 1;
        constraints.weighty = 1;

        // Menu title (row 0)
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 2;
        add(new JLabel("SELECT A MAP"), constraints);
        constraints.gridwidth = 1;

        // Map selection (row 1)
        constraints.gridy = 1;

        constraints.gridx = 0;
        add(new JLabel("Available maps:"), constraints);

        constraints.gridx = 1;
        InputStream in = MapMenu.class.getResourceAsStream("mapIndex");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        JComboBox<String> mapBox = new JComboBox<>(reader.lines().toArray(String[]::new));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(mapBox, constraints);
        constraints.fill = GridBagConstraints.NONE;

        // Back and start buttons (row 2)
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        JButton back = new JButton("Back");
        back.addActionListener(e -> mainController.loadHomeMenu());
        add(back, constraints);

        constraints.gridx = 1;
        JButton start = new JButton("Start");
        start.addActionListener(e -> mainController.loadGame((String) mapBox.getSelectedItem()));
        add(start, constraints);

        constraints.fill = GridBagConstraints.NONE;
    }
}
