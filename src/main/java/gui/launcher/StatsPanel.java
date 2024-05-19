package gui.launcher;

import model.ingame.Statistics;

import javax.swing.*;
import java.awt.*;

/**
 * A panel displaying the statistics of a game that has ended.
 */
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

        constraints.gridy = 2;
        String upgradeMessage = String.format(
                "You collected %d speed %s, %d damage %s,",
                stats.nbSpeedUpgrade,
                stats.nbSpeedUpgrade <= 1 ? "upgrade" : "upgrades",
                stats.nbDamageUpgrade,
                stats.nbDamageUpgrade <= 1 ? "upgrade" : "upgrades"
        );
        add(new JLabel(upgradeMessage), constraints);

        constraints.gridy = 3;
        String upgradeMessage2LeRetour = String.format(
                "%d health %s and %d regen %s.",
                stats.nbHealthUpgrade,
                stats.nbHealthUpgrade <= 1 ? "upgrade" : "upgrades",
                stats.nbRegenUpgrade,
                stats.nbRegenUpgrade <= 1 ? "upgrade" : "upgrades"
        );
        add(new JLabel(upgradeMessage2LeRetour), constraints);
    }
}
