package model.level.tiles;

import model.ingame.entity.PlayerModel;
import model.ingame.entity.WeaponEntity;
import model.level.TileModel;

public class SafeTileModel extends TileModel {

    public SafeTileModel() {
        addCanEnterCondition(entity ->
            entity instanceof PlayerModel || entity instanceof WeaponEntity
        );
    }
}
