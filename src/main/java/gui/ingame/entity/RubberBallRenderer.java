package gui.ingame.entity;

import model.ingame.entity.EntityModel;

public class RubberBallRenderer extends AbstractEntityRenderer {
    public RubberBallRenderer(EntityModel entityModel) {
        super(entityModel);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.setColor(java.awt.Color.RED);
        g.fillOval(0, 0, getWidth(), getHeight());
    }
}
