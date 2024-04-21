package gui.ingame;

import java.util.Set;

import javax.swing.JLayeredPane;

import gui.FillLayout;
import gui.ingame.effects.EffectsLayer;
import gui.ingame.footprints.FootprintsLayer;
import model.ingame.GameModel;
import util.IUpdateable;
import model.level.MapModel;

/**
 * The main area of the game, containing the map and the entities but NOT the HUD, buttons to exit, etc.
 */
public class GameMainArea extends JLayeredPane implements IUpdateable {
    private static final Integer TILES_LAYER = 0;
    private static final Integer FOOTPRINTS_LAYER = 5; // Footprints are drawn below entities (but on top of tiles)
    private static final Integer ENTITIES_LAYER = 10;
    private static final Integer HUD_LAYER = 30;

    private final Set<IUpdateable> layers;
    private final int mapWidth, mapHeight;

    GameMainArea(GameModel gameModel) {
        MapModel map = gameModel.getMapModel();
        mapWidth = map.getWidth();
        mapHeight = map.getHeight();

        MapBackgroundLayer mapBackgroundLayer = new MapBackgroundLayer(map, this::getScale);
        FootprintsLayer footprintsLayer = new FootprintsLayer(gameModel.getEntitySet(), this::getScale);
        EntitiesLayer entitiesLayer = new EntitiesLayer(gameModel.getEntitySet(), this::getScale);
        EffectsLayer effectsLayer = new EffectsLayer(gameModel, this::getScale);
        HUDLayer HUDLayer = new HUDLayer(gameModel.getPlayer(), this::getScale);
        layers = Set.of(mapBackgroundLayer, footprintsLayer, entitiesLayer, effectsLayer, HUDLayer);

        setLayout(new FillLayout());
        add(mapBackgroundLayer, TILES_LAYER);
        add(footprintsLayer, FOOTPRINTS_LAYER);
        add(entitiesLayer, ENTITIES_LAYER);
        add(effectsLayer, HUD_LAYER);
        add(HUDLayer, HUD_LAYER);
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
    public void update(double delta) {
        for (IUpdateable layer : layers) {
            layer.update(delta);
        }
    }
}
