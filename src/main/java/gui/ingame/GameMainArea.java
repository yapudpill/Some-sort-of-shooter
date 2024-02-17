package gui.ingame;

import gui.CenterFillRatioLayout;
import model.ingame.GameModel;
import model.ingame.IUpdateable;

import javax.swing.*;
import java.awt.*;

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
        // debug
        layeredPane.setBackground(Color.CYAN);
        layeredPane.setOpaque(true);

        CenterFillRatioLayout centerFillRatioLayout = new CenterFillRatioLayout();
        layeredPane.setLayout(centerFillRatioLayout); // Make the layered panes take all the space
        layeredPane.add(mapBackgroundPaneLayer.getJComponent(), TILES_LAYER);
        layeredPane.add(entitiesPaneLayer.getJComponent(), ENTITIES_LAYER);
        layeredPane.add(effectsPaneLayer, HUD_LAYER);
        centerFillRatioLayout.setWidthHeightRatio((double) gameModel.getMapModel().getWidth() / gameModel.getMapModel().getHeight());
        centerFillRatioLayout.setComponentCentering(mapBackgroundPaneLayer.getJComponent(), true);
        centerFillRatioLayout.setComponentCentering(entitiesPaneLayer.getJComponent(), true);
        centerFillRatioLayout.setComponentCentering(effectsPaneLayer, true);
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
     * @return the JComponent rendering the game
     */
    public JComponent getJComponent() {
        return layeredPane;
    }
}
