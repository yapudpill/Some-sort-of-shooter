package model.ingame.entity;

import model.ingame.GameModel;
import model.level.MapModel;
import util.Coordinates;
import util.IUpdateable;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Spawner that randomly places entities obtained from a supplier of EntityConstructor on the map.
 */
public class RandomPositionSpawner implements IUpdateable {
    private final Supplier<EntityConstructor> entityFactorySupplier;
    private final Random rng = new Random();
    private final GameModel gameModel;
    protected final MapModel mapModel;

    public RandomPositionSpawner(GameModel gameModel, Supplier<EntityConstructor> entityFactorySupplier) {
        this.gameModel = gameModel;
        this.mapModel = gameModel.getMapModel();
        this.entityFactorySupplier = entityFactorySupplier;
    }

    @Override
    public void update(double delta) {
        // Try to spawn a new entity
        EntityConstructor constructor = entityFactorySupplier.get();
        if (constructor == null) return;

        IEntity entity = constructor.makeEntity(Coordinates.ZERO, gameModel);
        Coordinates pos = randomSpawn(entity);
        entity.setPos(pos);

        gameModel.addEntity(entity);
    }

    private Coordinates randomSpawn(IEntity entity) {
        int width = mapModel.getWidth();
        int height = mapModel.getHeight();

        // Select random position
        double x = rng.nextDouble(width);
        double y = rng.nextDouble(height);

        while (!validSpawn(x, y, entity)) {
            x++;
            if (x >= width) {
                x %= width;
                y = (y + 1) % height;
            }
        }

        return new Coordinates(x, y);
    }

    private boolean validSpawn(double x, double y, IEntity entity) {
        int ix = (int) x;
        int iy = (int) y;

        // Check if the current tile is walkable
        if (!mapModel.getTile(ix, iy).canEnter(entity)) {
            return false;
        }

        // Check if any surrounding tile is non walkable
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = ix + i;
                int newY = iy + j;

                // Exclude borders
                if (mapModel.isOutOfBounds(newX, newY)
                    || !mapModel.getTile(newX, newY).canEnter(entity)) {
                    return false;
                }
            }
        }

        // All checks passed, the tile and its surroundings are walkable
        return true;
    }
}