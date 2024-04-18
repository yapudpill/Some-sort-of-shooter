package model.ingame.entity;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import util.Coordinates;

public class EnemySpawnerModel extends EntitySpawner {

    public EnemySpawnerModel(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    protected WalkingEnemyModel makeEntity(Coordinates pos) {
        return new WalkingEnemyModel(pos, gameModel);
    }
}
