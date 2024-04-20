package gui.ingame.entity;

import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;
import model.ingame.entity.FirstAidKit;

public class FirstAidKitRenderer extends AbstractEntityRenderer{

    public FirstAidKitRenderer(FirstAidKit entityModel) {
        super(entityModel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image;
        image = ImageCache.loadImage("sprites/firstaid.png", getClass());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
