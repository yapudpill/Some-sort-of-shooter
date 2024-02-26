package gui.ingame.tile;

import gui.ImageCache;
import gui.ingame.entity.PlayerRenderer;
import gui.ingame.tile.AbstractTileRenderer;
import model.level.TileModel;

import java.awt.*;
import java.io.IOException;

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
