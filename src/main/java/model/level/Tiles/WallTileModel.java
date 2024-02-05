package model.level.Tiles;

import model.level.TileModel;

public class WallTileModel extends TileModel {

    public WallTileModel(){

    }
    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void applyEffect() {
    }
}
