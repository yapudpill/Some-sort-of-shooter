package gui.launcher;

import controller.MainController;
import model.level.InvalidMapException;
import model.level.scenario.InvalidScenarioException;
import util.Resource;

import javax.swing.*;
import java.awt.*;

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
        add(new JLabel("SELECT A MAP"), constraints);

        // Map selection (row 1)
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.BOTH;

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
                Resource selectedMapResource = mapSelector.getSelectedMap();
                Resource selectedScenarioResource = scenarioSelector.getSelectedScenario();
                mainController.loadGame(selectedMapResource, selectedScenarioResource);
            } catch (InvalidMapException | InvalidScenarioException e) {
                JOptionPane.showMessageDialog(
                this,
                String.format("The selected %s is not in the correct format.", e instanceof InvalidMapException ? "map" : "scenario"),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            }
        });
        add(start, constraints);

        constraints.fill = GridBagConstraints.NONE;
    }
}
