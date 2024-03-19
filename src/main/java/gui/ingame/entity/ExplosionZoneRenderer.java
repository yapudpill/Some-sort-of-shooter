package gui.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.entity.EntityModel;

import java.awt.*;

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
