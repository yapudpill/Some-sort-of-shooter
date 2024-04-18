package model.ingame;

import model.ingame.entity.IEntity;
import util.Coordinates;

public abstract class EntitySpawner {
    protected final GameModel gameModel;

    public EntitySpawner(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public IEntity spawnEntity(Coordinates pos) {
        IEntity entity = makeEntity(pos);
        gameModel.addEntity(entity);
        return entity;
    }

    public final IEntity spawnEntity(double x, double y) {
        return spawnEntity(new Coordinates(x, y));
    }

    protected abstract IEntity makeEntity(Coordinates pos);

    public final IEntity makeEntity(double x, double y) {
        return makeEntity(new Coordinates(x, y));
    }
}
