package model.ingame.entity;

import java.util.List;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import util.Coordinates;

public class RandomEnemySpawner extends EntitySpawner {
    private static final List<EntityConstructor> availableEntities = List.of(
        WalkingEnemyModel::new,
        SmartEnemyModel::new,
        ExplodingEnemy::new,
        FirstAidKit::new
    );
    private static final int nbEntities = availableEntities.size();

    private final IEntity canEnterReference = new WalkingEnemyModel(Coordinates.ZERO, gameModel);

    public RandomEnemySpawner(GameModel gameModel, double cooldown) {
        super(gameModel, cooldown);
    }

    @Override
    protected IEntity makeEntity(Coordinates pos) {
        return availableEntities.get(rng.nextInt(nbEntities)).makeEntity(pos, gameModel);
    }

    @Override
    protected boolean validSpawn(double x, double y) {
        int ix = (int) x;
        int iy = (int) y;

        // Check if the current tile is walkable
        if (!mapModel.getTile(ix, iy).canEnter(canEnterReference)) {
            return false;
        }

        // Check if any surrounding tile is non walkable
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = ix + i;
                int newY = iy + j;

                if (mapModel.isOutOfBounds(newX, newY)
                    || !mapModel.getTile(newX, newY).canEnter(canEnterReference)) {
                    return false;
                }
            }
        }

        return true;
    }
}
