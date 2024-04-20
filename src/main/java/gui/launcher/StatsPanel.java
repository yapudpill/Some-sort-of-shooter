package gui.launcher;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ingame.Statistics;

public class StatsPanel extends JPanel {

    public StatsPanel(Statistics stats) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        int time = (int) stats.survivedTime;

        constraints.gridy = 0;
        String timeMessage = String.format(
            "You survived %d min %d sec.",
            time / 60,
            time % 60
        );
        add(new JLabel(timeMessage), constraints);

        constraints.gridy = 1;
        String killMessage = String.format(
            "You killed %d %s by attacking %d %s.",
            stats.killedEnemies,
            stats.killedEnemies <= 1 ? "enemy" : "enemies",
            stats.nbAttacks,
            stats.nbAttacks <= 1 ? "time" : "times"
        );
        add(new JLabel(killMessage), constraints);
    }
}
