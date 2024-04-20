package gui.ingame.entity;


import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;
import model.ingame.entity.WeaponEntity;
import util.Coordinates;

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

        Image image;
        image = ImageCache.loadImage(String.format("sprites/weapon/%s.png", ((WeaponEntity) entity).getWeapon().getIdentifier()), getClass());

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
