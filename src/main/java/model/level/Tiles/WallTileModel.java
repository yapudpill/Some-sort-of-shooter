package model.level.Tiles;

import model.ingame.entity.IVulnerableEntity;
import model.level.TileModel;

/**
 * basic wall tile : no effect and not walkable
 */
public class WallTileModel extends TileModel {

    public WallTileModel(){

    }
    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void applyEffect(IVulnerableEntity entity) {
    }
}
