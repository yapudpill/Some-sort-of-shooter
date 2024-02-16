package gui.ingame;

import gui.FillLayout;
import gui.SquareGridLayout;
import model.ingame.IUpdateable;
import model.level.MapModel;

import javax.swing.*;
import java.awt.*;

public class MapRenderer implements IUpdateable {
    private static final Integer TILES_LAYER = 0;
    private final JLayeredPane layeredPane;
    private final JPanel tilesPanel;
    private final AbstractTileRenderer[][] tileRenderers;
    private final MapModel mapModel;

    public int getScale() {
        return Math.min(layeredPane.getWidth() / mapModel.getWidth(), layeredPane.getHeight() / mapModel.getHeight());
    }



    public MapRenderer(MapModel mapModel) {
        this.mapModel = mapModel;
        tileRenderers = new AbstractTileRenderer[mapModel.getHeight()][mapModel.getWidth()];
        tilesPanel = new JPanel();
        tilesPanel.setLayout(new SquareGridLayout(mapModel.getWidth(), this::getScale));
        this.layeredPane = new JLayeredPane();
        layeredPane.setBackground(Color.CYAN);
        layeredPane.setLayout(new FillLayout());
        layeredPane.add(tilesPanel, TILES_LAYER);
        tilesPanel.setBackground(Color.BLUE);

        for (int y = 0; y < mapModel.getHeight(); y++) {
            for (int x = 0; x < mapModel.getWidth(); x++) {
                AbstractTileRenderer tileRenderer = TileRendererFactory.makeTileRenderer(mapModel.getTile(x, y));
                tileRenderers[y][x] = tileRenderer;
                tilesPanel.add(tileRenderer);
            }
        }

    }

    @Override
    public void update() {

    }

    public JComponent getJComponent() {
        return layeredPane;
    }
}
