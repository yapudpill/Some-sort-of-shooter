package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.entity.EntityModel;
import util.Coordinates;

public class DebugDamageZoneRenderer extends AbstractEntityRenderer {
    public DebugDamageZoneRenderer(EntityModel entityModel) {
        super(entityModel);
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(entity.getHeight(), entity.getWidth());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
