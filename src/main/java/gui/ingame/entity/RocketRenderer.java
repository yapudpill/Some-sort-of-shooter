package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.weapon.RocketProjectileModel;

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
