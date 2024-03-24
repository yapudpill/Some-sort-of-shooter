package gui.ingame.entity;

import java.io.IOException;

import gui.ImageCache;
import model.ingame.Coordinates;
import model.ingame.entity.FirstAidKit;

import java.awt.Graphics;
import java.awt.Image;

public class FirstAidKitRenderer extends AbstractEntityRenderer{

    public FirstAidKitRenderer(FirstAidKit entityModel) {
        super(entityModel);
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(entityModel.getWidth(), entityModel.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image;
        try {
            image = ImageCache.loadImage("sprites/firstaid.png", PlayerRenderer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
