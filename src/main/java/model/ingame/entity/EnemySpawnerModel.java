package model.ingame.entity;

import java.util.Random;

import model.ingame.Coordinates;
import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import model.ingame.IUpdateable;
import model.ingame.weapon.PistolModel;

public class EnemySpawnerModel extends EntitySpawner implements IUpdateable {
    final static public int ENEMY_SPAWN_COOLDOWN = 15 * 60; // 15 seconds, i.e. 15 * 60 ticks
    Random rng = new Random();
    double spawnCooldown = 0;

    public EnemySpawnerModel(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void update() {
        spawnCooldown--;
        if (spawnCooldown <= 0) {
            double x = rng.nextDouble(gameModel.getMapModel().getWidth());
            double y = rng.nextDouble(gameModel.getMapModel().getHeight());
            // Move to the next walkable tile
            do {
                ++x;
                if (x >= gameModel.getMapModel().getWidth()) {
                    x = 0;
                    ++y;
                    if (y >= gameModel.getMapModel().getHeight()) {
                        y = 0;
                    }
                }
            } while (!gameModel.getMapModel().getTile((int) x, (int) y).isWalkable());
            spawnEntity(x, y);
            spawnCooldown = ENEMY_SPAWN_COOLDOWN;
        }
    }

    @Override
    public WalkingEnemyModel spawnEntity(double x, double y) {
        WalkingEnemyModel entity = (WalkingEnemyModel) super.spawnEntity(x, y);
        gameModel.getMapModel().addCollidableAt(entity, (int) x, (int) y);
        gameModel.getUpdateables().add(entity);
        return entity;
    }

    @Override
    protected WalkingEnemyModel makeEntity(double x, double y) {
        return new WalkingEnemyModel(gameModel.getPlayer(), gameModel.getPhysicsEngine(), new Coordinates(x, y));
    }
}
