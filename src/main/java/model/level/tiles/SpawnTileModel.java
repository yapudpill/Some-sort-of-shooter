package model.level.tiles;

import model.ingame.entity.PlayerModel;
import model.level.TileModel;

public class SpawnTileModel extends TileModel {

    public SpawnTileModel() {
        addCanEnterCondition(entity -> entity instanceof PlayerModel);
    }
}
