package model.ingame.physics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;

import model.ingame.entity.ICollisionEntity;

public class CollisionEvent extends EventObject {
    private final List<ICollisionEntity> involvedEntities;

    public CollisionEvent(ICollisionEntity source, ICollisionEntity... involved) {
        super(source);
        this.involvedEntities = new ArrayList<>();
        Collections.addAll(this.involvedEntities, involved);
    }

    public CollisionEvent(ICollisionEntity source, List<ICollisionEntity> involved) {
        super(source);
        this.involvedEntities = involved;
    }

    public List<ICollisionEntity> getInvolvedEntitiesList() {
        return involvedEntities;
    }

}
