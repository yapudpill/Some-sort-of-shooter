package gui.ingame.entity;

import java.awt.Graphics;

import model.ingame.entity.IVulnerableEntity;

class VulnerableSpriteRenderer extends SpriteRenderer {

    VulnerableSpriteRenderer(IVulnerableEntity entity, String path) {
        super(entity, path);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        HealthBarRenderer.drawBar(g, (IVulnerableEntity) entity, getWidth());
    }
}
