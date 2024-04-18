package model.level;

import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;

public interface ITileModel {

    boolean isWalkable();

    void applyEnterEffect(IEntity entity);

    void addCollidable(ICollisionEntity entity);
    void removeCollidable(ICollisionEntity entity);

}
