package model.ingame.entity;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import util.Coordinates;

public class SmartEnemySpawner extends EntitySpawner{

    public SmartEnemySpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    protected SmartEnemyModel makeEntity(Coordinates pos) {
        return new SmartEnemyModel(pos, gameModel);
    }
}
