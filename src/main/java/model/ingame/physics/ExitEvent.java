package model.ingame.physics;

import java.util.Set;

import model.ingame.entity.ICollisionEntity;

public class ExitEvent {
    private ICollisionEntity source;
    private Set<ICollisionEntity> entitiesWhoExited;

    public ExitEvent(ICollisionEntity source, Set<ICollisionEntity> entitiesWhoExited) {
        this.source = source;
        this.entitiesWhoExited = entitiesWhoExited;
    }

    public ICollisionEntity getSource() {
        return source;
    }

    public Set<ICollisionEntity> getEntitiesWhoExited() {
        return entitiesWhoExited;
    }

}
