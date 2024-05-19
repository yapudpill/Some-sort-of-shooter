package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import util.Coordinates;

import java.util.HashSet;
import java.util.Set;

/**
 * An entity that deals damage to all entities it collides with. Automatically despawns after a set duration.
 */
public class ExplosionZoneEntity extends CollisionEntityModel {
    private final ModelTimer despawnTimer;

    private final Set<IVulnerableEntity> hitEntities = new HashSet<>();

    public ExplosionZoneEntity(Coordinates pos, double width, double height, int damage, int duration, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.despawnTimer = new ModelTimer(duration, false, this::despawn, gameModel);
        this.despawnTimer.start();

        addCollisionListener(e -> {
            for (ICollisionEntity entity: e.getInvolvedEntitiesList()) {
                if (entity instanceof IVulnerableEntity vulnerableEntity && !hitEntities.contains(vulnerableEntity)) {
                    vulnerableEntity.takeDamage(damage);
                    hitEntities.add(vulnerableEntity);
                }
            }
        });
    }
}
