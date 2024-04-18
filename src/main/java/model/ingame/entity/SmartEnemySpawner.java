package model.ingame.entity;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import util.Coordinates;

public class SmartEnemySpawner extends EntitySpawner{

        public SmartEnemySpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public SmartEnemyModel spawnEntity(double x, double y) {
        SmartEnemyModel entity = (SmartEnemyModel) super.spawnEntity(x, y);
        gameModel.addEntity(entity);
        return entity;
    }

    @Override
    protected SmartEnemyModel makeEntity(double x, double y) {
        return new SmartEnemyModel(new Coordinates(x, y), gameModel);
    }
}
