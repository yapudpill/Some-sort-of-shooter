package model.ingame;

import model.ingame.entity.IEntity;

public abstract class EntitySpawner {
    protected GameModel gameModel;


    public EntitySpawner(GameModel gameModel) {
        this.gameModel = gameModel;
    }
    public IEntity spawnEntity(double x, double y) {
        IEntity entity = makeEntity(x, y);
        gameModel.addEntity(entity);
        return entity;
    }

    protected abstract IEntity makeEntity(double x, double y);
}
