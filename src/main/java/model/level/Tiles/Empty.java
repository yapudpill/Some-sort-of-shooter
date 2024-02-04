package model.level.Tiles;

import model.level.TileModel;

public class Empty extends TileModel {

    public Empty(){

    }
    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public void applyEffect() {
    }
}
