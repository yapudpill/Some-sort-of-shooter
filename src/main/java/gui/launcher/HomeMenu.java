package gui.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;

public class HomeMenu extends JPanel {

    public HomeMenu(MainController controller) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;

        // First line
        constraints.gridy = 0;

        constraints.gridx = 0;
        constraints.gridwidth = 2;
        JLabel title = new JLabel("SOME SORT OF SHOOTER");
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title, constraints);
        constraints.gridwidth = 1;

        // Second line
        constraints.gridy = 1;

        constraints.gridx = 0;
        JButton levels = new JButton("Play");
        levels.addActionListener(e -> controller.loadGame());
        add(levels, constraints);

        constraints.gridx = 1;
        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> controller.closeWindow());
        add(exit, constraints);
    }
}
