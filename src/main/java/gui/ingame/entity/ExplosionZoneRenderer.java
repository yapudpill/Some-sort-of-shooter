package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.entity.EntityModel;

public class ExplosionZoneRenderer extends AbstractEntityRenderer {
    public ExplosionZoneRenderer(EntityModel entityModel) {
        super(entityModel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw an ORANGE rectangle filled
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
