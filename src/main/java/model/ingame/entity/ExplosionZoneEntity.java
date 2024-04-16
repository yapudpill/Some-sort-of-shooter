package model.ingame.entity;

import java.util.HashSet;
import java.util.Set;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import util.ModelTimer;

public class ExplosionZoneEntity extends CollisionEntityModel {
    private final ModelTimer despawnTimer;

    private final Set<IVulnerableEntity> hitEntities = new HashSet<>();

    public ExplosionZoneEntity(Coordinates pos, double width, double height, int damage, int duration, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.despawnTimer = new ModelTimer(duration, this::despawn, gameModel);
        this.despawnTimer.setRepeats(false);
        this.despawnTimer.start();

        addCollisionListener(e -> {
            for (ICollisionEntity entity: e.getInvolvedEntitiesList()) {
                if (entity instanceof IVulnerableEntity vulnerableEntity && !hitEntities.contains(vulnerableEntity)) {
                    if(vulnerableEntity instanceof IEffectEntity effectEntity && !effectEntity.canApplyEffect(vulnerableEntity)) {
                        continue;
                    }
                    vulnerableEntity.takeDamage(damage);
                    hitEntities.add(vulnerableEntity);
                    System.out.println("explosion hit");
                }
            }
        });
    }
}
