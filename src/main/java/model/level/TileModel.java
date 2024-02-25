package model.level;

import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class TileModel implements ITileModel {
    protected final List<TileContent> tileContents = new ArrayList<>();
    protected final List<ICollisionEntity> collidables = new ArrayList<>();

    public TileModel() {

    }

    public abstract boolean isWalkable();

    @Override
    public void applyEnterEffect(IEntity entity) {
    }


    public void addTileContent(TileContent content) {
        if (content == null) throw new IllegalArgumentException("Content cannot be null");
        tileContents.add(content);
    }

    public void removeTileContent(TileContent content) {
        if (content == null) throw new IllegalArgumentException("Content cannot be null");
        tileContents.remove(content);
    }

    public void addCollidable(ICollisionEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        collidables.add(entity);
    }

    public void removeCollidable(ICollisionEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity cannot be null");
        collidables.remove(entity);
    }

    public Iterator<ICollisionEntity> getCollidablesIterator() {
        return collidables.iterator();
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
