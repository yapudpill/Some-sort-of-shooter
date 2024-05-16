package gui.ingame.tile;

import gui.ImageCache;

import java.awt.Graphics;
import java.awt.Image;

public class StaticTileImageRenderer extends TileRenderer{
    protected Image image;

    public StaticTileImageRenderer(String path) {
        image = ImageCache.loadImage(path, getClass());
    }

    public StaticTileImageRenderer(){
        image = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
