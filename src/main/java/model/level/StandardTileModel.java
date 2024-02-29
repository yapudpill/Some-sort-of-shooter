package model.level;

import java.util.List;

import model.ingame.entity.IEntity;

public class StandardTileModel extends TileModel {
    @Override
    public boolean isWalkable() {
        return true;
    }

}
