package model.ingame;

import java.util.Random;

import model.ingame.entity.IEntity;
import model.level.MapModel;
import util.Coordinates;
import util.IUpdateable;

public abstract class EntitySpawner implements IUpdateable {
    protected final Random rng = new Random();
    protected final GameModel gameModel;
    protected final MapModel mapModel;
    private final double cooldown;
    private double timeLeft;

    protected EntitySpawner(GameModel gameModel, double cooldown) {
        this.gameModel = gameModel;
        this.mapModel = gameModel.getMapModel();
        this.cooldown = cooldown;
    }

    @Override
    public void update(double delta) {
        timeLeft -= delta;
        if (timeLeft <= 0) {
            int width = mapModel.getWidth();
            int height = mapModel.getHeight();

            // Select random position
            double x = rng.nextDouble(width);
            double y = rng.nextDouble(height);

            // Move to the next walkable tile
            while (!validSpawn(x, y)) {
                x++;
                if (x >= width) {
                    x %= width;
                    y = (y + 1) % height;
                }
            }

            // Spawn entity
            IEntity entity = makeEntity(new Coordinates(x, y));
            gameModel.addEntity(entity);

            timeLeft = cooldown;
        }
    }

    protected abstract IEntity makeEntity(Coordinates pos);

    protected abstract boolean validSpawn(double x, double y);
}
