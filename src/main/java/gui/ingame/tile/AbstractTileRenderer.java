package gui.ingame.tile;

import java.awt.Graphics;

import javax.swing.JPanel;

import model.level.TileModel;

public abstract class AbstractTileRenderer extends JPanel {
    private final TileModel tileModel;
    public AbstractTileRenderer(TileModel tileModel) {
        this.tileModel = tileModel;
        setOpaque(true);
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);

    }
}
