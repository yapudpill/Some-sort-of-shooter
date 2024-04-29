package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.entity.IVulnerableEntity;

class HealthBarRenderer {

    static void drawBar(Graphics g, IVulnerableEntity entity, int width) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, 2);

        g.setColor(Color.GREEN);
        int greenWidth = width * entity.getHealth() / entity.getMaxHealth();
        g.fillRect(0, 0, greenWidth, 2);
    }
}
