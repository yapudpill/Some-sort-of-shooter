package model.level.Tiles;

import model.ingame.entity.IVulnerableEntity;
import model.level.TileModel;

/**
 * basic tile : no effect and walkable
 */
public class DefaultTileModel extends TileModel {

    public DefaultTileModel(){

    }
    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public void applyEffect(IVulnerableEntity entity) {
    }
}
