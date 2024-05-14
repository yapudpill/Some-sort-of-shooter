package gui.ingame.tile;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import gui.ImageCache;

public class TileRenderer extends JPanel {
    protected Image image;

    public TileRenderer(String path) {
        image = ImageCache.loadImage(path, getClass());
    }

    public TileRenderer(){
        image = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
