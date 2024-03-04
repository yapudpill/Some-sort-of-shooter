package model.ingame.entity;

import java.util.Random;

import model.ingame.Coordinates;
import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import model.ingame.IUpdateable;

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
            Coordinates res = findNextWalkableTile((int) x,(int) y);
            spawnEntity(res.x, res.y);
            spawnCooldown = ENEMY_SPAWN_COOLDOWN;
        }
    }

    private Coordinates findNextWalkableTile(int x, int y) {
        int width = gameModel.getMapModel().getWidth();
        int height = gameModel.getMapModel().getHeight();

        do {
            x = (x + 1) % width;
            if (x == 0) {
                y = (y + 1) % height;
            }
            System.out.println("x: " + x + " y: " + y + " isWalkable: " + gameModel.getMapModel().getTile((int) x, (int) y).isWalkable());

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

        // Check if any surrounding tile is unwalkable
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;

                // Exclude borders
                if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
                    return false;
                }

                // Exclude tiles surrounding an unwalkable tile
                if (!gameModel.getMapModel().getTile(newX, newY).isWalkable()) {
                    return false;
                }
            }
        }

        // All checks passed, the tile and its surroundings are walkable
        return true;
    }

    @Override
    public WalkingEnemyModel spawnEntity(double x, double y) {
        WalkingEnemyModel entity = (WalkingEnemyModel) super.spawnEntity(x, y);
        gameModel.addEntity(entity);
        gameModel.attachAsUpdateable(entity);
        return entity;
    }

    @Override
    protected WalkingEnemyModel makeEntity(double x, double y) {
        return new WalkingEnemyModel(new Coordinates(x, y), gameModel);
    }
}
