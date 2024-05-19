package model.ingame.physics;

import model.ingame.entity.ICollisionEntity;

import java.util.Collections;
import java.util.EventObject;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Event that is fired when a collision occurs.
 */
public class CollisionEvent extends EventObject {
    private final Set<ICollisionEntity> involvedEntities;

    public CollisionEvent(ICollisionEntity source, ICollisionEntity... involved) {
        super(source);
        this.involvedEntities = new CopyOnWriteArraySet<>();
        Collections.addAll(this.involvedEntities, involved);
    }

    public CollisionEvent(ICollisionEntity source, Set<ICollisionEntity> involved) {
        super(source);
        this.involvedEntities = involved;
    }

    public Set<ICollisionEntity> getInvolvedEntitiesList() {
        return involvedEntities;
    }

}
