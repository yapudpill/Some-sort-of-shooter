package model.level.tiles;

import model.ingame.entity.IEntity;
import model.ingame.weapon.ProjectileModel;
import model.level.TileModel;

public class WaterTileModel extends TileModel {
    @Override
    public boolean isWalkable() {
        return false;
    }


    @Override
    public boolean canEnter(IEntity entity) {
        return entity instanceof ProjectileModel;
    }
}
