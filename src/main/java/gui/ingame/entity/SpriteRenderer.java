package gui.ingame.entity;

import gui.ImageCache;
import model.ingame.entity.IEntity;

import java.awt.*;
import java.util.function.DoubleSupplier;

/**
 * Renderer for entities that are represented by a single sprite.
 */
public class SpriteRenderer extends EntityRenderer {
    private final Image image;
    private final DoubleSupplier angle;

    public SpriteRenderer(IEntity entity, String path, DoubleSupplier angle) {
        super(entity);
        this.image = ImageCache.loadImage(path, getClass());
        this.angle = angle;
    }

    public SpriteRenderer(IEntity entity, String path) {
        this(entity, path, () -> 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g.create();
        double mid = (double) getWidth() / 2;
        g2D.rotate(angle.getAsDouble(), mid, mid);
        g2D.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
