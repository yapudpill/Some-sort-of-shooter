package gui.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.entity.EntityModel;

import java.awt.*;

public class DebugDamageZoneRenderer extends AbstractEntityRenderer {
    public DebugDamageZoneRenderer(EntityModel entityModel) {
        super(entityModel);
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(entityModel.getHeight(), entityModel.getWidth());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
