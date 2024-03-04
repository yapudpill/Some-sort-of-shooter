package gui.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.entity.EntityModel;
import model.ingame.weapon.IProjectile;

public class ProjectileRenderer extends AbstractEntityRenderer{

    public ProjectileRenderer(EntityModel entityModel) {
        super(entityModel);
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(entityModel.getWidth(), entityModel.getHeight());
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.setColor(java.awt.Color.BLACK);
        g.fillOval(0, 0, getWidth(), getHeight());
    }

}
