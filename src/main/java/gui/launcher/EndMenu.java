package gui.launcher;

import controller.MainController;
import model.ingame.Statistics;
import model.level.InvalidMapException;
import model.level.scenario.InvalidScenarioException;

import javax.swing.*;
import java.awt.*;

public class EndMenu extends JPanel {

    public EndMenu(MainController mainController, Statistics stats) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.weightx = 1;
        constraints.weighty = 1;

        // Title (row 0)
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        add(new JLabel("GAME OVER"), constraints);
        constraints.gridwidth = 1;

        // Statistics (row 1)
        constraints.gridy = 1;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        add(new StatsPanel(stats), constraints);
        constraints.gridwidth = 1;

        // Menu and replay (row 2)
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        JButton menu = new JButton("Menu");
        menu.addActionListener(e -> mainController.loadHomeMenu());
        add(menu, constraints);

        constraints.gridx = 2;
        JButton replay = new JButton("Replay");
        replay.addActionListener(event -> {
            try {
                mainController.loadGame(stats.mapResource, stats.scenarioResource);
            } catch (InvalidMapException | InvalidScenarioException e) {
                JOptionPane.showMessageDialog(
                this,
                String.format("The %s file changed and is no longer in the correct format.", e instanceof InvalidMapException ? "map" : "animation"),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            }
        });
        add(replay, constraints);

        constraints.gridwidth = 1;

        // Quit (row 3)
        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> mainController.closeWindow());
        add(quit, constraints);
        constraints.gridwidth = 1;

        // Needed to properly separate the 4 columns (row 4)
        constraints.gridy = 4;
        constraints.weighty = 0;
        for (int i = 0; i < 4; i++) {
            constraints.gridx = i;
            add(new JPanel(), constraints);
        }
    }

}
