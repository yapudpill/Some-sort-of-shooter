package gui.ingame.entity;

import model.ingame.entity.IVulnerableEntity;

import java.awt.*;

/**
 * Utility class for rendering health bars.
 */
class HealthBarRenderer {

    static void drawBar(Graphics g, IVulnerableEntity entity, int width) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, 2);

        g.setColor(Color.GREEN);
        int greenWidth = width * entity.getHealth() / entity.getMaxHealth();
        g.fillRect(0, 0, greenWidth, 2);
    }
}
