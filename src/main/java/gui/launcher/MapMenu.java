package gui.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.MainController;
import model.level.InvalidMapException;

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
        MapSelector selector = new MapSelector();
        add(selector, constraints);

        constraints.gridwidth = 1;

        // Back and start buttons (row 2)
        constraints.gridy = 2;
        constraints.weighty = 0.5;

        constraints.gridx = 0;
        JButton back = new JButton("Back");
        back.addActionListener(e -> mainController.loadHomeMenu());
        add(back, constraints);

        constraints.gridx = 1;
        JButton start = new JButton("Start");
        start.addActionListener(event -> {
            try {
                mainController.loadGame(selector.getSelectedMap());
            } catch (InvalidMapException e) {
                JOptionPane.showMessageDialog(
                this,
                "The selected file is not in the correct format.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            }
        });
        add(start, constraints);

        constraints.fill = GridBagConstraints.NONE;
    }
}
