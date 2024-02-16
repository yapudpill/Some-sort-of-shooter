package gui.ingame;

import gui.ScaleSupplier;
import gui.SquareGridLayout;
import model.level.MapModel;

import javax.swing.*;
import java.awt.*;

public class MapBackgroundRenderer {
    private final JPanel tilesPanel;

    private final AbstractTileRenderer[][] tileRenderers;

    public MapBackgroundRenderer(MapModel mapModel, ScaleSupplier scaleSupplier) {
        this.tilesPanel = new JPanel();

        this.tileRenderers = new AbstractTileRenderer[mapModel.getHeight()][mapModel.getWidth()];
        // debug
        tilesPanel.setBackground(Color.BLUE);
        this.tilesPanel.setLayout(new SquareGridLayout(mapModel.getWidth(), scaleSupplier));

        for (int y = 0; y < mapModel.getHeight(); y++) {
            for (int x = 0; x < mapModel.getWidth(); x++) {
                AbstractTileRenderer tileRenderer = TileRendererFactory.makeTileRenderer(mapModel.getTile(x, y));
                tileRenderers[y][x] = tileRenderer;
                tilesPanel.add(tileRenderer);
            }
        }
    }

    public JComponent getJComponent() {
        return tilesPanel;
    }
}
