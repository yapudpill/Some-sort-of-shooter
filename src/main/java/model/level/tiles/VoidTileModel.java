package model.level.tiles;

import model.level.TileModel;

/**
 * used to fill the blank space out of map bounds (to be displayed in black or another background color)
 */
public class VoidTileModel extends TileModel {

    public VoidTileModel() {
        addCanEnterCondition(entity -> false);
    }
}
