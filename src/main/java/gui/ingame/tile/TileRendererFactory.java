package gui.ingame.tile;

import model.level.TileModel;
import model.level.tiles.StandardTileModel;
import model.level.tiles.VoidTileModel;
import model.level.tiles.WaterTileModel;
import model.level.tiles.SpawnTileModel;

public class TileRendererFactory {
    static public AbstractTileRenderer makeTileRenderer(TileModel tileModel) {
        return switch (tileModel) {
            case StandardTileModel t -> new StandardTileRenderer();
            case WaterTileModel t -> new WaterTileRenderer();
            case VoidTileModel t -> new VoidTileRenderer();
            case SpawnTileModel t -> new SpawnTileRenderer();
            default -> new StandardTileRenderer(); // Fallback to normal renderer
        };
    }
}
