package model.level.tiles;

import model.ingame.weapon.Projectile;
import model.level.TileModel;

public class WaterTileModel extends TileModel {

    public WaterTileModel() {
        addCanEnterCondition(entity -> entity instanceof Projectile);
    }
}
