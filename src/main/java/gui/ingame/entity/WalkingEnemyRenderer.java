package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.awt.Image;

import gui.ImageCache;
import model.ingame.Coordinates;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.entity.WalkingEnemyModel;

public class WalkingEnemyRenderer extends AbstractEntityRenderer {
    public WalkingEnemyRenderer(WalkingEnemyModel entityModel) {
        super(entityModel);
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(entityModel.getWidth(), entityModel.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image;
        try {
            image = ImageCache.loadImage("sprites/EyeBallEnemy.png", PlayerRenderer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        // show health bar
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), 5);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, (int) (getWidth() * ((double) ((IVulnerableEntity) entityModel).getHealth() / ((IVulnerableEntity) entityModel).getMaxHealth())), 5);
    }
}
