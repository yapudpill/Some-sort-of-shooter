package gui.ingame;

import gui.FillLayout;
import gui.ingame.effects.EffectsPaneLayer;
import gui.ingame.footprints.FootprintsLayer;
import model.ingame.GameModel;
import model.ingame.IUpdateable;
import model.level.MapModel;

import javax.swing.*;
import java.awt.*;

/**
 * The main area of the game, containing the map and the entities but NOT the HUD, buttons to exit, etc.
 */
public class GameMainArea extends JLayeredPane implements IUpdateable {
    private static final Integer TILES_LAYER = 0;
    private static final Integer FOOTPRINTS_LAYER = 5;
    private static final Integer ENTITIES_LAYER = 10;
    private static final Integer HUD_LAYER = 30;

    private final MapBackgroundPaneLayer mapBackgroundPaneLayer;
    private final FootprintsLayer footprintsLayer;
    private final EntitiesPaneLayer entitiesPaneLayer;
    private final EffectsPaneLayer effectsPaneLayer;
    private final GameHUDLayer gameHUDLayer;

    private final int mapWidth, mapHeight;

    public GameMainArea(GameModel gameModel) {
        MapModel map = gameModel.getMapModel();
        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        mapBackgroundPaneLayer = new MapBackgroundPaneLayer(map, this::getScale);
        footprintsLayer = new FootprintsLayer(gameModel, this::getScale);
        entitiesPaneLayer = new EntitiesPaneLayer(gameModel.getEntitySet(), this::getScale);
        effectsPaneLayer = new EffectsPaneLayer(gameModel, this::getScale);
        gameHUDLayer = new GameHUDLayer(gameModel.getPlayer(), this::getScale);

        // debug
        setBackground(Color.CYAN);
        setOpaque(true);

        setLayout(new FillLayout());
        add(mapBackgroundPaneLayer.getJComponent(), TILES_LAYER);
        add(footprintsLayer, FOOTPRINTS_LAYER); // Footprints are drawn below entities (but on top of tiles)
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
        footprintsLayer.update();
        entitiesPaneLayer.update();
        effectsPaneLayer.update();
        gameHUDLayer.update();
    }
}
