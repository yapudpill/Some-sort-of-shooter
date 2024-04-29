package model.level.tiles;

import model.ingame.entity.IEntity.IEntityFactory;
import model.level.TileModel;

public class StandardTileModel extends TileModel {
    @Override
    public boolean canSpawn(IEntityFactory entity) {
        return true;
    }
}
