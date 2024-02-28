package model.level;

import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;

import java.util.List;

public interface ITileModel {
    List<TileContent> getTileContents();
    List<ICollisionEntity> getCollidables();

    boolean isWalkable();

    void applyEnterEffect(IEntity entity);

    void addCollidable(ICollisionEntity entity);
    void removeCollidable(ICollisionEntity entity);

    void addTileContent(TileContent content);
    void removeTileContent(TileContent content);
}
