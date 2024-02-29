package model.level;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;

public abstract class TileModel implements ITileModel {
    protected final List<ICollisionEntity> collidables = new CopyOnWriteArrayList<>();

    public TileModel() {

    }

    public abstract boolean isWalkable();

    @Override
    public void applyEnterEffect(IEntity entity) {
    }

    public void addCollidable(ICollisionEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        collidables.add(entity);
    }

    public void removeCollidable(ICollisionEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        collidables.remove(entity);
    }

    public List<ICollisionEntity> getCollidablesList() {
        return collidables;
    }

    @Override
    public List<ICollisionEntity> getCollidables() {
        return collidables;
    }

    @Override
    public List<TileContent> getTileContents() {
        return tileContents;
    }

    @Override
    public List<ICollisionEntity> getCollidables() {
        return collidables;
    }
}
