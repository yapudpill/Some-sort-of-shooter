package gui.ingame.entity;

import model.ingame.entity.IVulnerableEntity;

import java.awt.*;
import java.util.function.DoubleSupplier;

/**
 * Same as SpriteRenderer but with a health bar.
 */
public class VulnerableSpriteRenderer extends SpriteRenderer {

    public VulnerableSpriteRenderer(IVulnerableEntity entity, String path, DoubleSupplier angle) {
        super(entity, path, angle);
    }

    public VulnerableSpriteRenderer(IVulnerableEntity entity, String path) {
        super(entity, path);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        HealthBarRenderer.drawBar(g, (IVulnerableEntity) entity, getWidth());
    }
}
