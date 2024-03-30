package gui.ingame;

import java.awt.Color;

import javax.swing.JLayeredPane;

import gui.FillLayout;
import model.ingame.GameModel;
import model.ingame.IUpdateable;
import model.level.MapModel;

/**
 * The main area of the game, containing the map and the entities but NOT the HUD, buttons to exit, etc.
 */
public class GameMainArea extends JLayeredPane implements IUpdateable {
    private static final Integer TILES_LAYER = 0;
    private static final Integer ENTITIES_LAYER = 10;
    private static final Integer HUD_LAYER = 30;

    private final MapBackgroundPaneLayer mapBackgroundPaneLayer;
    private final EntitiesPaneLayer entitiesPaneLayer;
    private final EffectsPaneLayer effectsPaneLayer;
    private final GameHUDLayer gameHUDLayer;

    private final int mapWidth, mapHeight;

    public GameMainArea(GameModel gameModel) {
        MapModel map = gameModel.getMapModel();
        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        mapBackgroundPaneLayer = new MapBackgroundPaneLayer(map, this::getScale);
        entitiesPaneLayer = new EntitiesPaneLayer(gameModel.getEntitySet(), this::getScale);
        effectsPaneLayer = new EffectsPaneLayer(gameModel, this::getScale);
        gameHUDLayer = new GameHUDLayer(gameModel.getPlayer(), this::getScale);

        // debug
        setBackground(Color.CYAN);
        setOpaque(true);

        setLayout(new FillLayout());
        add(mapBackgroundPaneLayer.getJComponent(), TILES_LAYER);
        add(entitiesPaneLayer.getJComponent(), ENTITIES_LAYER);
        add(effectsPaneLayer, HUD_LAYER);
        add(gameHUDLayer, HUD_LAYER);
    }

    /**
     * Return the scale of the map, i.e. the size of one tile (in pixels)
     *
     * @return the scale of the map
     */
    public int getScale() {
        return Math.min(getWidth() / mapWidth, getHeight() / mapHeight);
    }

    @Override
    public void update() {
        mapBackgroundPaneLayer.update();
        entitiesPaneLayer.update();
        effectsPaneLayer.update();
        gameHUDLayer.update();
    }
}
