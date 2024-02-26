package gui.ingame.entity;


import model.ingame.Coordinates;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.entity.WeaponEntity;

import java.awt.*;
import java.io.IOException;

import gui.ImageCache;

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
        try {
            image = ImageCache.loadImage("/gui/ingame/pistol.png", PlayerRenderer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
