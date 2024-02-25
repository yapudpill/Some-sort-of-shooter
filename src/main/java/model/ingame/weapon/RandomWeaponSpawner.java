package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import model.ingame.IUpdateable;
import model.ingame.entity.IEntity;
import model.ingame.entity.WeaponEntity;
import model.ingame.weapon.PistolModel;

import java.util.Random;

public class RandomWeaponSpawner extends EntitySpawner implements IUpdateable {
    final static public int WEAPON_SPAWN_COOLDOWN = 5 * 60; // 5 seconds, i.e. 5 * 60 ticks
    Random rng = new Random();
    double spawnCooldown = 0;
    public RandomWeaponSpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void update() {
        spawnCooldown--;
        if (spawnCooldown <= 0) {
            double x = rng.nextDouble(gameModel.getMapModel().getWidth());
            double y = rng.nextDouble(gameModel.getMapModel().getHeight());
            spawnEntity(x, y);
            spawnCooldown = WEAPON_SPAWN_COOLDOWN;
        }
    }

    @Override
    public WeaponEntity spawnEntity(double x, double y) {
        WeaponEntity entity = (WeaponEntity) super.spawnEntity(x, y);
        gameModel.getMapModel().addCollidableAt(entity, (int) x, (int) y);
        return entity;
    }

    @Override
    protected WeaponEntity makeEntity(double x, double y) {
        return new WeaponEntity(new Coordinates(x, y), new PistolModel(null, gameModel.getPhysicsEngine()));
    }
}
