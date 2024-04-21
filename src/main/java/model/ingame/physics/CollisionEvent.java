package model.ingame.physics;

import java.util.Collections;
import java.util.EventObject;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import model.ingame.entity.ICollisionEntity;

public class CollisionEvent extends EventObject {
    private final Set<ICollisionEntity> involvedEntities;

    CollisionEvent(ICollisionEntity source, ICollisionEntity... involved) {
        super(source);
        this.involvedEntities = new CopyOnWriteArraySet<>();
        Collections.addAll(this.involvedEntities, involved);
    }

    CollisionEvent(ICollisionEntity source, Set<ICollisionEntity> involved) {
        super(source);
        this.involvedEntities = involved;
    }

    public Set<ICollisionEntity> getInvolvedEntitiesList() {
        return involvedEntities;
    }

}
