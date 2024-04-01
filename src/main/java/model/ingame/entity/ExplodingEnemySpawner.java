package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.EntitySpawner;
import model.ingame.GameModel;

public class ExplodingEnemySpawner extends EntitySpawner {

    public ExplodingEnemySpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public ExplodingEnemy spawnEntity(double x, double y) {
        ExplodingEnemy entity = (ExplodingEnemy) super.spawnEntity(x, y);
        gameModel.addEntity(entity);
        gameModel.attachAsUpdateable(entity);
        return entity;
    }

    @Override
    protected ExplodingEnemy makeEntity(double x, double y) {
        return new ExplodingEnemy(new Coordinates(x, y), gameModel);
    }

}
