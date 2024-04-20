package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.entity.EntityModel;

public class DebugDamageZoneRenderer extends AbstractEntityRenderer {
    public DebugDamageZoneRenderer(EntityModel entityModel) {
        super(entityModel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
