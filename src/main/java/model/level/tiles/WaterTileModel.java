package model.level.tiles;

import model.level.TileModel;

public class WaterTileModel extends TileModel {
    @Override
    public boolean isWalkable() {
        return false;
    }

}
