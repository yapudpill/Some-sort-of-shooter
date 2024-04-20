package gui.ingame.tile;

import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;

public class WaterTileRenderer extends AbstractTileRenderer {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
                Image image;
        image = ImageCache.loadImage("WaterTile.png", getClass());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

}
