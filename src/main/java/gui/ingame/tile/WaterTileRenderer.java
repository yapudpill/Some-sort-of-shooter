package gui.ingame.tile;

import gui.ingame.tile.AbstractTileRenderer;
import model.level.TileModel;

import java.awt.*;

public class WaterTileRenderer extends AbstractTileRenderer {
    public WaterTileRenderer(TileModel tileModel) {
        super(tileModel);
        setBackground(Color.CYAN);
    }
}
