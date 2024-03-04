package gui.ingame.tile;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import gui.ImageCache;
import gui.ingame.entity.PlayerRenderer;
import model.level.TileModel;

public class WaterTileRenderer extends AbstractTileRenderer {
    public WaterTileRenderer(TileModel tileModel) {
        super(tileModel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
                Image image;
        try {
            image = ImageCache.loadImage("/gui/ingame/WaterTile.png", PlayerRenderer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

}
