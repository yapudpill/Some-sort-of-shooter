package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.entity.IVulnerableEntity;

public class VulnerableRenderer extends SpriteRenderer {

    public VulnerableRenderer(IVulnerableEntity entity, String path) {
        super(entity, path);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // show health bar
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), 5);

        g.setColor(Color.GREEN);
        IVulnerableEntity vul = (IVulnerableEntity) entity;
        int greenWidth = (int) (getWidth() * ((double) vul.getHealth() / vul.getMaxHealth()));
        g.fillRect(0, 0, greenWidth, 5);
    }
}
