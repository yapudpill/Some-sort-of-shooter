package gui.ingame.entity;

import gui.ImageCache;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.entity.SmartEnemyModel;
import util.Coordinates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;


public class SmartEnemyRenderer extends AbstractEntityRenderer{
    public SmartEnemyRenderer(SmartEnemyModel entityModel) {
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
        image = ImageCache.loadImage("sprites/Brain_of_Cthulhu.png", PlayerRenderer.class);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        // show health bar
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), 5);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, (int) (getWidth() * ((double) ((IVulnerableEntity) entityModel).getHealth() / ((IVulnerableEntity) entityModel).getMaxHealth())), 5);
    }
}
