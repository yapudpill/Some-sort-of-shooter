package model.level.tiles;

import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import model.level.TileModel;

public class SpawnTileModel extends TileModel {
    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public boolean canEnter(IEntity entity) {
        return entity instanceof PlayerModel;
    }
}
