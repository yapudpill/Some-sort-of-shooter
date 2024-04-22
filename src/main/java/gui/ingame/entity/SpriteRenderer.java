package gui.ingame.entity;

import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;
import model.ingame.entity.IEntity;

public class SpriteRenderer extends AbstractEntityRenderer {
    private final Image image;

    public SpriteRenderer(IEntity entity, String path) {
        super(entity);
        image = ImageCache.loadImage(path, getClass());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
