package gui.ingame;

import gui.CenterFillRatioLayout;
import gui.FillLayout;
import model.ingame.GameModel;
import model.ingame.IUpdateable;

import javax.swing.*;
import java.awt.*;

/**
 * The main area of the game, containing the map and the entities but NOT the HUD, buttons to exit, etc.
 */
public class GameMainArea implements IUpdateable {
    private static final Integer TILES_LAYER = 0;
    private static final Integer ENTITIES_LAYER = 10;
    private static final Integer HUD_LAYER = 20;

    private final JLayeredPane layeredPane;

    private final MapBackgroundPaneLayer mapBackgroundPaneLayer;
    private final EntitiesPaneLayer entitiesPaneLayer;
    private final EffectsPaneLayer effectsPaneLayer;

    private final GameModel gameModel;

    public GameMainArea(GameModel gameModel) {
        this.gameModel = gameModel;
        this.mapBackgroundPaneLayer = new MapBackgroundPaneLayer(gameModel.getMapModel(), this::getScale);
        this.entitiesPaneLayer = new EntitiesPaneLayer(gameModel.getEntitySet(), this::getScale);
        this.effectsPaneLayer = new EffectsPaneLayer();

        this.layeredPane = new JLayeredPane();
        layeredPane.setLayout(new FillLayout());
        // debug
        layeredPane.setBackground(Color.CYAN);
        layeredPane.setOpaque(true);

        //layeredPane.setLayout(new FlowLayout()); // Make the layered panes take all the space
        layeredPane.add(mapBackgroundPaneLayer.getJComponent(), TILES_LAYER);
        layeredPane.add(entitiesPaneLayer.getJComponent(), ENTITIES_LAYER);
        layeredPane.add(effectsPaneLayer, HUD_LAYER);
    }

    /**
     * Return the scale of the map, i.e. the size of one tile (in pixels)
     *
     * @return the scale of the map
     */
    public int getScale() {
        return Math.min(layeredPane.getWidth() / gameModel.getMapModel().getWidth(), layeredPane.getHeight() / gameModel.getMapModel().getHeight());
    }

    @Override
    public void update() {
        mapBackgroundPaneLayer.update();
        entitiesPaneLayer.update();
    }

    /**
     * @return the JComponent displaying the game area
     */
    public JComponent getJComponent() {
        return layeredPane;
    }
}
