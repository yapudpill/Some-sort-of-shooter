package model.ingame.entity;

import java.util.List;
import java.util.Random;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import model.ingame.ModelTimer;
import util.Coordinates;

public class RandomSpawnerModel {
    private final ModelTimer spawnTimer;
    private final GameModel gameModel;
    private final Random rng = new Random();

    public RandomSpawnerModel(GameModel gameModel, List<EntitySpawner> spawners, int delay) {
        this.gameModel = gameModel;
        this.spawnTimer = new ModelTimer(delay, true, () -> {
            double x = rng.nextDouble(gameModel.getMapModel().getWidth());
            double y = rng.nextDouble(gameModel.getMapModel().getHeight());
            int index = rng.nextInt(spawners.size());
            // Move to the next walkable tile
            Coordinates res = findNextWalkableTile((int) x,(int) y);
            spawners.get(index).spawnEntity(res.x(), res.y());
        }, gameModel);
        this.spawnTimer.start();
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
        } while (!isTileAndSurroundingsWalkable((int) x, (int) y));

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

    public void stop() {
        spawnTimer.stop();
    }

    public void start() {
        spawnTimer.start();
    }

}
