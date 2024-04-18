package model.ingame.entity;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import util.Coordinates;

public class ExplodingEnemySpawner extends EntitySpawner {

    public ExplodingEnemySpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    protected ExplodingEnemy makeEntity(Coordinates pos) {
        return new ExplodingEnemy(pos, gameModel);
    }

}
