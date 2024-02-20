package model.level;

import model.ingame.entity.IVulnerableEntity;

public class StandardTileModel extends TileModel {
    @Override
    public boolean isWalkable() {
        return true;
    }

    @Override
    public void applyEffect(IVulnerableEntity entity) {

    }
}
