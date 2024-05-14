package gui.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.MainController;
import model.ingame.Statistics;
import model.level.InvalidMapException;
import model.level.scenario.InvalidScenarioException;

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

        JLabel title = new JLabel("GAME OVER");
        title.setName("titleLabel");
        add(title, constraints);

        // Statistics (row 1)
        constraints.gridy = 1;
        constraints.gridx = 0;
        add(new StatsPanel(stats), constraints);

        // Insets for the rest of the components
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = 2;

        // Menu and replay (row 2)
        constraints.gridy = 2;

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
                String.format("The %s file changed and is no longer in the correct format.", e instanceof InvalidMapException ? "map" : "scenario"),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            }
        });
        add(replay, constraints);


        // Quit (row 3)
        constraints.gridy = 3;
        constraints.gridx = 1;

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> mainController.closeWindow());
        add(quit, constraints);

        // Quite ridiculous but needed to properly separate the 4 columns (row 4)
        constraints.gridy = 4;
        constraints.weighty = 0;
        constraints.gridwidth = 1;
        for (int i = 0; i < 4; i++) {
            constraints.gridx = i;
            add(new JPanel(), constraints);
        }
    }

}
