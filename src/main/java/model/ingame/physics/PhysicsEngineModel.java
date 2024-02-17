package model.ingame.physics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import model.ingame.Coordinates;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IMobileEntity;
import model.level.MapModel;

public class PhysicsEngineModel {
    private final MapModel map;

    public PhysicsEngineModel(MapModel map) {
        if (map == null)
            throw new IllegalArgumentException("Map cannot be null");
        this.map = map;
    }

    public List<ICollisionEntity> getCollidedEntities(ICollisionEntity entity) {
        List<ICollisionEntity> involvedEntities = new ArrayList<>();
        List<Iterator<ICollisionEntity>> iterators = map.getAllCollidableIteratorsAround((int) entity.getPos().x,(int) entity.getPos().y);
        for (Iterator<ICollisionEntity> iterator : iterators) {
            while (iterator.hasNext()) {
                ICollisionEntity other = iterator.next();
                if (entity.getCollisionBox().intersects(other.getCollisionBox())) {
                    involvedEntities.add(other);
                }
            }
        }
        return involvedEntities;
    }

    public void move(IMobileEntity entity, Coordinates newPos){
        Coordinates oldPos = entity.getPos();
        if(entity == null || newPos == null)
            throw new IllegalArgumentException("Entity or newPos cannot be null");
        else if(map.isOutOfBounds((int) newPos.x, (int) newPos.y)){
            entity.getMovementHandler().setDirectionVector(Coordinates.ZERO);
            return;
        }
        else if(!map.isWalkableAt((int) newPos.x, (int) newPos.y)){
            entity.getMovementHandler().setDirectionVector(Coordinates.ZERO);
            return;
        }
        else if(!entity.getPos().intEquals(newPos)){
            map.removeCollidableAt(entity, (int) oldPos.x, (int) oldPos.y);
            map.addCollidableAt(entity, (int) newPos.x, (int) newPos.y);
        }
        else if(entity.hasCollisionListeners()){
            List<ICollisionEntity> involvedEntities = getCollidedEntities(entity);
            if(!involvedEntities.isEmpty()){
                CollisionEvent event = new CollisionEvent(entity, involvedEntities.toArray(new ICollisionEntity[0]));
                entity.notifyCollisionListeners(event);
                involvedEntities.forEach(e -> e.notifyCollisionListeners(event));
            }
        }
        entity.setPos(newPos);
        entity.setColisionBox(newPos.x, newPos.y);
    }
}
