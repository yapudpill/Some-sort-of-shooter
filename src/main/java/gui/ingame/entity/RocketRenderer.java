package gui.ingame.entity;

import model.ingame.entity.EntityModel;
import model.ingame.weapon.RocketProjectileModel;

import java.awt.*;

public class RocketRenderer extends ProjectileRenderer {
    public RocketRenderer(RocketProjectileModel entityModel) {
        super(entityModel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(0, 0, getWidth(), getHeight());
    }
}
