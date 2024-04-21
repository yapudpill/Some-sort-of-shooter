package gui.ingame;

import java.awt.GridLayout;
import java.util.function.IntSupplier;

import javax.swing.JPanel;

import gui.ingame.tile.TileRenderer;
import gui.ingame.tile.TileRendererFactory;
import model.level.MapModel;
import util.IUpdateable;

/**
 * A layer of the game rendering the map background. It is a JPanel containing TileRenderers, each one corresponding
 * to a tile of the map.
 */
class MapBackgroundLayer extends JPanel implements IUpdateable {
    private final TileRenderer[][] tileRenderers;

    MapBackgroundLayer(MapModel mapModel, IntSupplier scaleSupplier) {
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
    public void update(double delta) {}
}
