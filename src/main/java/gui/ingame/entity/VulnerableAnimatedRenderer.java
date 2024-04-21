package gui.ingame.entity;

import java.awt.Graphics;
import java.util.function.DoubleSupplier;

import model.ingame.entity.IVulnerableEntity;

class VulnerableAnimatedRenderer extends AnimatedRenderer {

    VulnerableAnimatedRenderer(IVulnerableEntity entity, String path, DoubleSupplier angle) {
        super(entity, path, angle);
    }

    VulnerableAnimatedRenderer(IVulnerableEntity entity, String path) {
        super(entity, path);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        HealthBarRenderer.drawBar(g, (IVulnerableEntity) entity, getWidth());
    }
}
