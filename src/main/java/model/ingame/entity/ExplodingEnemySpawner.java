package model.ingame.entity;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import util.Coordinates;

public class ExplodingEnemySpawner extends EntitySpawner {

    public ExplodingEnemySpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public ExplodingEnemy spawnEntity(double x, double y) {
        ExplodingEnemy entity = (ExplodingEnemy) super.spawnEntity(x, y);
        return entity;
    }

    @Override
    protected ExplodingEnemy makeEntity(double x, double y) {
        return new ExplodingEnemy(new Coordinates(x, y), gameModel);
    }

}
