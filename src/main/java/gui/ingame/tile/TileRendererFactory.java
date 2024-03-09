package gui.ingame.tile;

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
            default -> {
                System.out.println("TileRendererFactory: unknown tile model: " + tileModel.getClass().getName());
                yield null; // TODO: should we throw an exception here? or return a default renderer?
            }
        };
    }
}
