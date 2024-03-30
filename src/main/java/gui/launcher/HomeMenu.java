package gui.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;

public class HomeMenu extends JPanel {

    public HomeMenu(MainController mainController) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.weightx = 1;

        // Title (row 0)
        constraints.gridy = 0;
        constraints.weighty = 1;

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        add(new JLabel("SOME SORT OF SHOOTER"), constraints);
        constraints.gridwidth = 1;

        // Play and editor (row 1)
        constraints.gridy = 1;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        JButton play = new JButton("Play");
        play.addActionListener(e -> mainController.loadMapMenu());
        add(play, constraints);

        constraints.gridx = 1;
        JButton edit = new JButton("Editor");
        edit.addActionListener(e -> mainController.loadEditor());
        add(edit, constraints);

        // Quit (row 2)
        constraints.gridy = 2;
        constraints.gridx = 0;
        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> mainController.closeWindow());
        add(quit, constraints);
    }
}
