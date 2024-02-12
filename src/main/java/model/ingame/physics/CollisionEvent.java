package model.ingame.physics;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import model.ingame.entity.ICollisionEntity;

public class CollisionEvent extends EventObject {
    private final List<ICollisionEntity> involvedEntities;

    public CollisionEvent(ICollisionEntity source, ICollisionEntity... involved) {
        super(source);
        this.involvedEntities = new ArrayList<>();
        for (ICollisionEntity entity : involved) {
            this.involvedEntities.add(entity);
        }
    }

    public Iterator<ICollisionEntity> getInvolvedEntitiesIterator() {
        return involvedEntities.iterator();
    }
    
}
