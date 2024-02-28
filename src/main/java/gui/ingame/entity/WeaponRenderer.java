package gui.ingame.entity;


import model.ingame.Coordinates;
import model.ingame.entity.WeaponEntity;

import java.awt.*;

public class WeaponRenderer extends AbstractEntityRenderer {
    public WeaponRenderer(WeaponEntity entityModel) {
        super(entityModel);
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(0.5,0.5);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
