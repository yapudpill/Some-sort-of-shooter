package gui.ingame.tile;

import model.level.TileModel;
import model.level.tiles.SafeTileModel;
import model.level.tiles.StandardTileModel;
import model.level.tiles.VoidTileModel;
import model.level.tiles.WaterTileModel;

/**
 * A factory for creating tile renderers.
 */
public class TileRendererFactory {
    public static TileRenderer make(TileModel tileModel) {
        return switch (tileModel) {
            case StandardTileModel ignored -> new StaticTileImageRenderer("GrassTile.jpg");
            case WaterTileModel ignored    -> new AnimatedTileRenderer("animations/water.xml");
            case VoidTileModel ignored     -> new StaticTileImageRenderer("VoidTile.png");
            case SafeTileModel ignored     -> new StaticTileImageRenderer("SafeTile.jpg");

            default -> throw new IllegalArgumentException("Unknown tile model " + tileModel.getClass().getName());
        };
    }
}
