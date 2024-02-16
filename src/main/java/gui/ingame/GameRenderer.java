package gui.ingame;

import gui.FillLayout;
import model.ingame.IUpdateable;
import model.level.MapModel;

import javax.swing.*;
import java.awt.*;

public class GameRenderer implements IUpdateable {
    private static final Integer TILES_LAYER = 0;
    private final JLayeredPane layeredPane;

    private final MapBackgroundRenderer mapBackgroundRenderer;
    private final MapModel mapModel;

    public GameRenderer(MapModel mapModel) {
        this.mapModel = mapModel;

        this.mapBackgroundRenderer = new MapBackgroundRenderer(mapModel, this::getScale);

        this.layeredPane = new JLayeredPane();
        // debug
        layeredPane.setBackground(Color.CYAN);

        layeredPane.setLayout(new FillLayout()); // Make the layered panes take all the space
        layeredPane.add(mapBackgroundRenderer.getJComponent(), TILES_LAYER);
    }

    /**
     * Return the scale of the map, i.e. the size of one tile (in pixels)
     *
     * @return the scale of the map
     */
    public int getScale() {
        return Math.min(layeredPane.getWidth() / mapModel.getWidth(), layeredPane.getHeight() / mapModel.getHeight());
    }

    @Override
    public void update() {

    }

    /**
     * @return the JComponent rendering the game
     */
    public JComponent getJComponent() {
        return layeredPane;
    }
}
