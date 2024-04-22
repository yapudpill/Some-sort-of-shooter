package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import util.IUpdateable;

import java.util.Random;
import java.util.function.Supplier;

import static model.ingame.entity.IEntity.IEntityFactory;

public class RandomPositionSpawner implements IUpdateable {
    private final GameModel gameModel;
    private final Supplier<IEntityFactory> entityFactorySupplier;
    private final Random rng = new Random();

    public RandomPositionSpawner(GameModel gameModel, Supplier<IEntityFactory> entityFactorySupplier) {
        this.gameModel = gameModel;
        this.entityFactorySupplier = entityFactorySupplier;
    }

    public void spawnEntity() {
        IEntityFactory entityFactory = entityFactorySupplier.get();
        if (entityFactory == null) return;
        double x = rng.nextInt(gameModel.getMapModel().getWidth());
        double y = rng.nextInt(gameModel.getMapModel().getHeight());
        // Move to the next walkable tile
        Coordinates pos = findNextWalkableTile((int) x, (int) y);
        gameModel.addEntity(entityFactory.make(pos, gameModel));
    }

    private Coordinates findNextWalkableTile(int x, int y) {
        int width = gameModel.getMapModel().getWidth();
        int height = gameModel.getMapModel().getHeight();

        do {
            x = (x + 1) % width;
            if (x == 0) {
                y = (y + 1) % height;
            }

            // Check if the current tile and its surrounding tiles are walkable
        } while (!isTileAndSurroundingsWalkable(x, y));

        return new Coordinates(x, y);
    }

    private boolean isTileAndSurroundingsWalkable(int x, int y) {
        int width = gameModel.getMapModel().getWidth();
        int height = gameModel.getMapModel().getHeight();

        // Check if the current tile is walkable
        if (!gameModel.getMapModel().getTile(x, y).isWalkable()) {
            return false;
        }

        // Check if any surrounding tile is non walkable
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;

                // Exclude borders
                if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
                    return false;
                }

                // Exclude tiles surrounding an non walkable tile
                if (!gameModel.getMapModel().getTile(newX, newY).isWalkable()) {
                    return false;
                }
            }
        }

        // All checks passed, the tile and its surroundings are walkable
        return true;
    }

    @Override
    public void update(double delta) {
        spawnEntity();
    }
}
