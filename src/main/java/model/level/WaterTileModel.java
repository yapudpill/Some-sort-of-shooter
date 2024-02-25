package model.level;

import model.ingame.entity.IVulnerableEntity;

public class WaterTileModel extends TileModel {
    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public void applyEffect(IVulnerableEntity entity) {

    }
}
