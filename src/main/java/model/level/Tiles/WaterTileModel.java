package model.level.Tiles;

import model.ingame.entity.IVulnerableEntity;
import model.level.TileModel;

public class WaterTileModel extends TileModel {
    @Override
    public boolean isWalkable() {
        return false;
    }

}
