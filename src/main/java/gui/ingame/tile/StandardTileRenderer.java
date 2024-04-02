package gui.ingame.tile;

import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;

public class StandardTileRenderer extends AbstractTileRenderer {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image;
        image = ImageCache.loadImage("GrassTile.png", getClass());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}

