package gui.ingame;

import gui.ScaleSupplier;
import gui.SquareGridLayout;
import gui.ingame.tile.AbstractTileRenderer;
import gui.ingame.tile.TileRendererFactory;
import model.ingame.IUpdateable;
import model.level.MapModel;

import javax.swing.*;
import java.awt.*;

/**
 * A layer of the game rendering the map background. It is a JPanel containing TileRenderers, each one corresponding
 * to a tile of the map.
 */
public class MapBackgroundPaneLayer implements IUpdateable {
    private final JPanel tilesPanel;

    private final AbstractTileRenderer[][] tileRenderers;

    public MapBackgroundPaneLayer(MapModel mapModel, ScaleSupplier scaleSupplier) {
        this.tilesPanel = new JPanel();
        this.tilesPanel.setLayout(new SquareGridLayout(mapModel.getWidth(), scaleSupplier));
        this.tilesPanel.setOpaque(true);

        this.tileRenderers = new AbstractTileRenderer[mapModel.getHeight()][mapModel.getWidth()];
        // debug
        tilesPanel.setBackground(Color.BLUE);

        for (int y = 0; y < mapModel.getHeight(); y++) {
            for (int x = 0; x < mapModel.getWidth(); x++) {
                AbstractTileRenderer tileRenderer = TileRendererFactory.makeTileRenderer(mapModel.getTile(x, y));
                tileRenderers[y][x] = tileRenderer;
                tilesPanel.add(tileRenderer);
            }
        }
    }

    /**
     * @return the JComponent of this layer
     */
    public JComponent getJComponent() {
        return tilesPanel;
    }

    @Override
    public void update() {
        tilesPanel.repaint();
    }
}
