package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.ExplosionZoneEntity;
import model.ingame.entity.IVulnerableEntity;

public class RocketProjectileModel extends ProjectileModel {
    public static final double ROCKET_SPEED = 0.05;
    public static final double ROCKET_WIDTH = 0.3;
    public static final double ROCKET_HEIGHT = 0.3;
    public static final int ROCKET_DAMAGE = 10;
    public static final int ROCKET_EXPLOSION_RADIUS = 2;

    public static final int ROCKET_EXPLOSION_DURATION = 60;

    public RocketProjectileModel(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, ROCKET_WIDTH, ROCKET_HEIGHT, ROCKET_DAMAGE, gameModel);
        movementHandler.setSpeed(ROCKET_SPEED);
        addCollisionListener(e -> {
            if (e.getSource() != source.getOwner()) {
                if (e.getInvolvedEntitiesList().stream().anyMatch(entity -> entity instanceof IVulnerableEntity vulnerableEntity && vulnerableEntity != source.getOwner())) {
                    explode();
                }
            }
        });
        addBlockedMovementListener(_ -> explode());
    }

    private void explode() {
        despawn();
        gameModel.addEntity(new ExplosionZoneEntity(getPos(), ROCKET_EXPLOSION_RADIUS, ROCKET_EXPLOSION_RADIUS, ROCKET_DAMAGE, ROCKET_EXPLOSION_DURATION, gameModel));
    }
}
