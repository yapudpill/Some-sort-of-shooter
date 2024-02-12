package model.level;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.ingame.Coordinates;
import model.ingame.entity.EntityModel;
import model.ingame.entity.ICollisionEntity;

public abstract class TileModel {
    protected final List<TileContent> tileContents = new ArrayList<>();
    protected final List<ICollisionEntity> collidables = new ArrayList<>(); 

    public TileModel() {
        //TODO: implement projectile
    }
    
    public void addTileContent(TileContent content) {
        if (content == null)
            throw new IllegalArgumentException("Content cannot be null");
        tileContents.add(content);
    }

    public void removeTileContent(TileContent content) {
        if (content == null)
            throw new IllegalArgumentException("Content cannot be null");
        tileContents.remove(content);
    }

    public void addCollidable(ICollisionEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        collidables.add(entity);
    }

    public void removeCollidable(ICollisionEntity entity) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        collidables.remove(entity);
    }

    public Iterator<ICollisionEntity> getCollidablesIterator() {
        return collidables.iterator();
    }
}
