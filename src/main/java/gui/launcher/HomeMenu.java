package gui.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MainController;
import gui.ImageCache;

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
        JLabel title = new JLabel("SOME SORT OF SHOOTER");
        title.setName("titleLabel");
        add(title, constraints);
        constraints.gridwidth = 1;

        // Play and editor (row 1)
        constraints.gridy = 1;
        constraints.weighty = 0.5;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(30, 30, 30, 30);

        constraints.gridx = 0;
        JButton play = new JButton("Play", ImageCache.loadIcon("play"));
        play.addActionListener(e -> mainController.loadMapMenu());
        add(play, constraints);

        constraints.gridx = 1;
        JButton edit = new JButton("Editor", ImageCache.loadIcon("file"));
        edit.addActionListener(e -> mainController.loadEditor());
        add(edit, constraints);

        // Quit (row 2)
        constraints.gridy = 2;
        constraints.gridx = 0;
        JButton quit = new JButton("Quit", ImageCache.loadIcon("exit"));
        quit.addActionListener(e -> mainController.closeWindow());
        add(quit, constraints);
    }
}
