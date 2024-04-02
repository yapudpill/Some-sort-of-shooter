package gui.ingame.tile;

import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;

public class VoidTileRenderer extends AbstractTileRenderer {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
                Image image;
        image = ImageCache.loadImage("VoidTile.png", getClass());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

}
