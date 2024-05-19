package gui.ingame.entity;

import model.ingame.entity.IVulnerableEntity;

import java.awt.*;
import java.util.function.DoubleSupplier;

/**
 * Renderer for entities that are represented by an animated sprite and should display a health bar.
 */
public class VulnerableAnimatedRenderer extends AnimatedEntityRenderer{
    private int lastHealth;
    private int alpha = 0;
    private int red = 0;
    private int green= 0;

    public VulnerableAnimatedRenderer(IVulnerableEntity entity, String path, DoubleSupplier angle) {
        super(entity, path, angle);
        lastHealth = entity.getHealth();
    }

    public VulnerableAnimatedRenderer(IVulnerableEntity entity, String path) {
        super(entity, path);
        lastHealth = entity.getHealth();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        HealthBarRenderer.drawBar(g, (IVulnerableEntity) entity, getWidth());
        g.setColor(new Color(red, green, 0, alpha));
        g.fillOval(0, 0, getWidth(), getHeight());
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        // Make the entity flash red or green when it takes damage or heals
        if(lastHealth > ((IVulnerableEntity) entity).getHealth()) {
            lastHealth = ((IVulnerableEntity) entity).getHealth();
            alpha = 128;
            red = 255;
            green = 0;
        } else if(lastHealth < ((IVulnerableEntity) entity).getHealth()) {
            lastHealth = ((IVulnerableEntity) entity).getHealth();
            alpha = 255;
            red = 0;
            green = 255;
        }
        if(alpha>0) {
            alpha=Math.max(0,alpha-5);
        }
    }
}
