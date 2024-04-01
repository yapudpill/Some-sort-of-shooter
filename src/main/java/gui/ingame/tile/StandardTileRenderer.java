package gui.ingame.tile;

import java.awt.Graphics;
import java.awt.Image;

import gui.ImageCache;
import gui.ingame.entity.PlayerRenderer;
import model.level.TileModel;

public class StandardTileRenderer extends AbstractTileRenderer {
    public StandardTileRenderer(TileModel tileModel) {
        super(tileModel);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image;
        image = ImageCache.loadImage("/gui/ingame/GrassSprite.png", PlayerRenderer.class);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}

