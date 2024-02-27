package gui.ingame.tile;

import model.level.TileModel;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractTileRenderer extends JPanel {
    private final TileModel tileModel;
    public AbstractTileRenderer(TileModel tileModel) {
        this.tileModel = tileModel;
        setOpaque(true);
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
        g.drawRect(0, 0, getWidth(), getHeight());
    }
}
