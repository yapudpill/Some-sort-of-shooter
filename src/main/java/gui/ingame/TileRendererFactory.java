package gui.ingame;

import gui.ingame.tile.AbstractTileRenderer;
import gui.ingame.tile.StandardTileRenderer;
import gui.ingame.tile.VoidTileRenderer;
import gui.ingame.tile.WaterTileRenderer;
import model.level.TileModel;
import model.level.tiles.StandardTileModel;
import model.level.tiles.VoidTileModel;
import model.level.tiles.WaterTileModel;

public class TileRendererFactory {
    static public AbstractTileRenderer makeTileRenderer(TileModel tileModel) {
        return switch (tileModel) {
            case StandardTileModel standardTileModel -> new StandardTileRenderer(standardTileModel);
            case WaterTileModel waterTileModel -> new WaterTileRenderer(waterTileModel);
            case VoidTileModel voidTileModel -> new VoidTileRenderer(voidTileModel);
            default -> new StandardTileRenderer(tileModel); // Fallback to normal renderer
        };
    }
}
