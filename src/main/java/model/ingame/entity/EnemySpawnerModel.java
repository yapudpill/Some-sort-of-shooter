package model.ingame.entity;

import java.util.Random;

import model.ingame.Coordinates;
import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import model.ingame.IUpdateable;

public class EnemySpawnerModel extends EntitySpawner {

    public EnemySpawnerModel(GameModel gameModel) {
        super(gameModel);
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
