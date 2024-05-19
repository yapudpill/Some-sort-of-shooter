package gui.ingame.tile;

import model.level.TileModel;
import model.level.tiles.StandardTileModel;
import model.level.tiles.VoidTileModel;
import model.level.tiles.WaterTileModel;
import model.level.tiles.SafeTileModel;

/**
 * A factory for creating tile renderers.
 */
public class TileRendererFactory {
    public static TileRenderer make(TileModel tileModel) {
        return switch (tileModel) {
            case StandardTileModel t -> new StaticTileImageRenderer("GrassTile.jpg");
            case WaterTileModel t    -> new AnimatedTileRenderer("animations/water.xml");
            case VoidTileModel t     -> new StaticTileImageRenderer("VoidTile.png");
            case SafeTileModel t     -> new StaticTileImageRenderer("SafeTile.jpg");

            default -> throw new IllegalArgumentException("Unknown tile model " + tileModel.getClass().getName());
        };
    }
}
