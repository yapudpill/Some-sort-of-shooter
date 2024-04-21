package gui.ingame.tile;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import gui.ImageCache;

public class TileRenderer extends JPanel {
    private final Image image;

    TileRenderer(String path) {
        image = ImageCache.loadImage(path, getClass());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
