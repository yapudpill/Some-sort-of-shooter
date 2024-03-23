package gui.ingame.entity;

import model.ingame.entity.EntityModel;

public class ProjectileRenderer extends AbstractEntityRenderer{

    public ProjectileRenderer(EntityModel entityModel) {
        super(entityModel);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.setColor(java.awt.Color.BLACK);
        g.fillOval(0, 0, getWidth(), getHeight());
    }
}
