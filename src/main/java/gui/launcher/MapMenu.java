package gui.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.MainController;
import model.level.InvalidMapException;
import model.level.scenario.InvalidScenarioException;
import util.Resource;

public class MapMenu extends JPanel {

    public MapMenu(MainController mainController) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.weightx = 1;

        // Menu title (row 0)
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 2;

        constraints.weighty = 0.5;
        JLabel title = new JLabel("MAP SELECTOR");
        title.setName("titleLabel");
        add(title, constraints);

        // Insets for the rest of the components
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Map selection (row 1)
        constraints.weighty = 1;
        MapSelector mapSelector = new MapSelector();
        add(mapSelector, constraints);

        constraints.gridy += 1;
        ScenarioSelector scenarioSelector = new ScenarioSelector();
        add(scenarioSelector, constraints);

        constraints.gridwidth = 1;

        // Back and start buttons (row 2)
        constraints.gridy += 2;
        constraints.weighty = 0.5;

        constraints.gridx = 0;
        JButton back = new JButton("Back");
        back.addActionListener(e -> mainController.loadHomeMenu());
        add(back, constraints);

        constraints.gridx = 1;
        JButton start = new JButton("Start");
        start.addActionListener(event -> {
            try {
                Resource map = mapSelector.getSelectedResource();
                Resource scenario = scenarioSelector.getSelectedResource();
                mainController.loadGame(map, scenario);
            } catch (InvalidMapException | InvalidScenarioException e) {
                JOptionPane.showMessageDialog(
                    this,
                    String.format("The selected %s is not formatted correctly.", e instanceof InvalidMapException ? "map" : "scenario"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        add(start, constraints);

        constraints.fill = GridBagConstraints.NONE;
    }
}
