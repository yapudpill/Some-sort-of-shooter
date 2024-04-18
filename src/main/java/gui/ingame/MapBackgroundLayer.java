package gui.ingame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import gui.ScaleSupplier;
import gui.ingame.tile.AbstractTileRenderer;
import gui.ingame.tile.TileRendererFactory;
import model.level.MapModel;
import util.IUpdateable;

/**
 * A layer of the game rendering the map background. It is a JPanel containing TileRenderers, each one corresponding
 * to a tile of the map.
 */
public class MapBackgroundLayer implements IUpdateable {
    private final JPanel tilesPanel;
    private final AbstractTileRenderer[][] tileRenderers;

    public MapBackgroundLayer(MapModel mapModel, ScaleSupplier scaleSupplier) {
        int width = mapModel.getWidth();
        int height = mapModel.getHeight();

        this.tilesPanel = new JPanel();
        this.tilesPanel.setLayout(new GridLayout(height, width));
        this.tilesPanel.setOpaque(true);

        this.tileRenderers = new AbstractTileRenderer[mapModel.getHeight()][mapModel.getWidth()];
        // debug
        tilesPanel.setBackground(Color.BLUE);

        for (int y = 0; y < mapModel.getHeight(); y++) {
            for (int x = 0; x < mapModel.getWidth(); x++) {
                AbstractTileRenderer tileRenderer = TileRendererFactory.make(mapModel.getTile(x, y));
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
    public void update(double delta) {
        tilesPanel.repaint();
    }
}
