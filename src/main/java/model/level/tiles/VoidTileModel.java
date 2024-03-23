package model.level.tiles;

import model.ingame.entity.IEntity;
import model.level.TileModel;

/**
 * used to fill the blank space out of map bounds (to be displayed in black or another background color)
 */
public class VoidTileModel extends TileModel {
    public VoidTileModel(){

    }
    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public boolean canEnter(IEntity entity) {
        return false;
    }
}
