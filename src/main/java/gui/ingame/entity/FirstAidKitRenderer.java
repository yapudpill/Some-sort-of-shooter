package gui.ingame.entity;

import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;
import model.ingame.entity.FirstAidKit;
import util.Coordinates;

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
        image = ImageCache.loadImage("sprites/firstaid.png", PlayerRenderer.class);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
