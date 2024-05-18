package gui.ingame.entity;

import gui.ImageCache;
import model.ingame.entity.IEntity;

import java.awt.*;

/**
 * Renderer for entities that are represented by a single sprite.
 */
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
