package gui.ingame.tile;

import model.level.TileModel;
import model.level.tiles.StandardTileModel;
import model.level.tiles.VoidTileModel;
import model.level.tiles.WaterTileModel;
import model.level.tiles.SpawnTileModel;

public class TileRendererFactory {
    public static TileRenderer make(TileModel tileModel) {
        return switch (tileModel) {
            case StandardTileModel t -> new TileRenderer("GrassTile.png");
            case WaterTileModel t    -> new TileRenderer("WaterTile.png");
            case VoidTileModel t     -> new TileRenderer("VoidTile.png");
            case SpawnTileModel t    -> new TileRenderer("SpawnTile.png");

            default -> throw new IllegalArgumentException("Unknown tile model " + tileModel.getClass().getName());
        };
    }
}
