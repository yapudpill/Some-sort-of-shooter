package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;
import model.ingame.entity.EntityModel;
import model.ingame.entity.IVulnerableEntity;

public class BreakableBarrierRenderer extends AbstractEntityRenderer {
    public BreakableBarrierRenderer(EntityModel entityModel) {
        super(entityModel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image;
        image = ImageCache.loadImage("sprites/breakablebarrier.png", BreakableBarrierRenderer.class);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                // show health bar
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), 5);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, (int) (getWidth() * ((double) ((IVulnerableEntity) entityModel).getHealth() / ((IVulnerableEntity) entityModel).getMaxHealth())), 5);
    }
}
