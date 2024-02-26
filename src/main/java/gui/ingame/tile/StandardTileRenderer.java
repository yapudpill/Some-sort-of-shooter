package gui.ingame.tile;

import gui.ImageCache;
import gui.ingame.entity.PlayerRenderer;
import gui.ingame.tile.AbstractTileRenderer;
import model.level.TileModel;

import java.awt.*;
import java.io.IOException;

public class StandardTileRenderer extends AbstractTileRenderer {
    public StandardTileRenderer(TileModel tileModel) {
        super(tileModel);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image;
        try {
            image = ImageCache.loadImage("/gui/ingame/GrassSprite.jpeg", PlayerRenderer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
    }

