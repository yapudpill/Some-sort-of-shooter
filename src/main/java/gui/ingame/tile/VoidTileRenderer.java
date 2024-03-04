package gui.ingame.tile;

import gui.ImageCache;
import gui.ingame.entity.PlayerRenderer;
import model.level.TileModel;

import java.awt.*;
import java.io.IOException;

public class VoidTileRenderer extends AbstractTileRenderer {
    public VoidTileRenderer(TileModel tileModel) {
        super(tileModel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
                Image image;
        try {
            image = ImageCache.loadImage("/gui/ingame/VoidTileSprite.png", PlayerRenderer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

}
