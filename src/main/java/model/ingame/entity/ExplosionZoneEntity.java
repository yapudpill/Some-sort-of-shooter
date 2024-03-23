package model.ingame.entity;

import java.util.HashSet;
import java.util.Set;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.physics.PhysicsEngineModel;
import util.ModelTimer;

public class ExplosionZoneEntity extends CollisionEntityModel {
    private final ModelTimer despawnTimer;

    private final Set<IVulnerableEntity> hitEntities = new HashSet<>();

    public ExplosionZoneEntity(Coordinates pos, double width, double height, int damage, int duration, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.despawnTimer = new ModelTimer(duration, this::despawn, gameModel);
        this.despawnTimer.start();

        addCollisionListener(e -> {
            for (ICollisionEntity entity: e.getInvolvedEntitiesList()) {
                if (entity instanceof IVulnerableEntity vulnerableEntity && !hitEntities.contains(vulnerableEntity)) {
                    vulnerableEntity.takeDamage(damage);
                    hitEntities.add(vulnerableEntity);
                    System.out.println("explosion hit");
                }
            }
        });

        // FIXME: workaround because the only way to be affected by collisions is by having a movementHandler, which is
        //  not required for static entities, making the separation between Colliding entities and moving entities very broken
        PhysicsEngineModel physicsEngine = gameModel.getPhysicsEngine();
        //physicsEngine.checkForCollisions(this);
    }
}
