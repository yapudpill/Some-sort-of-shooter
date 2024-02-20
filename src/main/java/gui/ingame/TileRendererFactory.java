package gui.ingame;

import gui.ingame.tile.AbstractTileRenderer;
import gui.ingame.tile.StandardTileRenderer;
import gui.ingame.tile.WaterTileRenderer;
import model.level.StandardTileModel;
import model.level.TileModel;
import model.level.WaterTileModel;

public class TileRendererFactory {
    static public AbstractTileRenderer makeTileRenderer(TileModel tileModel) {
        return switch (tileModel) {
            case StandardTileModel standardTileModel -> new StandardTileRenderer(standardTileModel);
            case WaterTileModel waterTileModel -> new WaterTileRenderer(waterTileModel);
            default -> new StandardTileRenderer(tileModel); // Fallback to normal renderer
        };
    }
}
