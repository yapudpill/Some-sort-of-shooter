package model.level.tiles;

import model.ingame.weapon.ProjectileModel;
import model.level.TileModel;

public class WaterTileModel extends TileModel {

    public WaterTileModel(){
        addCanEnterCondition(entity -> entity instanceof ProjectileModel);
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

}
