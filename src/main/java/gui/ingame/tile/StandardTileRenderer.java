package gui.ingame.tile;

import gui.ingame.tile.AbstractTileRenderer;
import model.level.TileModel;

import java.awt.*;

public class StandardTileRenderer extends AbstractTileRenderer {
    public StandardTileRenderer(TileModel tileModel) {
        super(tileModel);
        setBackground(Color.WHITE);
    }
}
