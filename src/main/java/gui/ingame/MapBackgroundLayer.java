package gui.ingame;

import gui.ingame.tile.TileRenderer;
import gui.ingame.tile.TileRendererFactory;
import model.level.MapModel;
import util.IUpdateable;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntSupplier;

/**
 * A layer of the game rendering the map background. It is a JPanel containing TileRenderers, each one corresponding
 * to a tile of the map.
 */
public class MapBackgroundLayer extends JComponent implements IUpdateable {
    private final TileRenderer[][] tileRenderers;

    public MapBackgroundLayer(MapModel mapModel, IntSupplier ignored) {
        int width = mapModel.getWidth();
        int height = mapModel.getHeight();

        setLayout(new GridLayout(height, width));
        setOpaque(true);

        this.tileRenderers = new TileRenderer[mapModel.getHeight()][mapModel.getWidth()];

        for (int y = 0; y < mapModel.getHeight(); y++) {
            for (int x = 0; x < mapModel.getWidth(); x++) {
                TileRenderer tileRenderer = TileRendererFactory.make(mapModel.getTile(x, y));
                tileRenderers[y][x] = tileRenderer;
                add(tileRenderer);
            }
        }
    }

    @Override
    public void update(double delta) {
        for (TileRenderer[] row : tileRenderers) {
            for (TileRenderer tileRenderer : row) {
                if (tileRenderer instanceof IUpdateable updateable) {
                    updateable.update(delta);
                }
            }
        }
    }
}
