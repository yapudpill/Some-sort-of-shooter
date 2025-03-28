package gui.ingame.tile;

import gui.ImageCache;

import java.awt.*;

/**
 * A renderer for static tiles, using a single image.
 */
public class StaticTileImageRenderer extends TileRenderer {
    private final Image image;

    public StaticTileImageRenderer(String path) {
        image = ImageCache.loadImage(path, getClass());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
