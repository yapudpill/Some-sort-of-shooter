package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import model.ingame.entity.WeaponEntity;

public class WeaponSpawner extends EntitySpawner {

    public WeaponSpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public WeaponEntity spawnEntity(double x, double y) {
        WeaponEntity entity = (WeaponEntity) super.spawnEntity(x, y);
        gameModel.getMapModel().addCollidableAt(entity, (int) x, (int) y);
        return entity;
    }

    @Override
    protected WeaponEntity makeEntity(double x, double y) {
        return new WeaponEntity(new Coordinates(x, y), new PistolModel(null, gameModel), gameModel);
    }
}
