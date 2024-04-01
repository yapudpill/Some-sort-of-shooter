package gui.ingame.entity;


import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import gui.ImageCache;
import model.ingame.Coordinates;
import model.ingame.entity.WeaponEntity;

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
        image = ImageCache.loadImage(String.format("sprites/weapon/%s.png", ((WeaponEntity) entityModel).getWeapon().getIdentifier()), getClass());

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
