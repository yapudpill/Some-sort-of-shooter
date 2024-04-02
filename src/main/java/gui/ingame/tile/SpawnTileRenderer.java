package gui.ingame.tile;

import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;

public class SpawnTileRenderer extends AbstractTileRenderer {

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = ImageCache.loadImage("SpawnTile.png", getClass());
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
