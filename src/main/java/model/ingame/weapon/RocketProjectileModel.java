package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ExplosionZoneEntity;
import model.ingame.entity.IVulnerableEntity;
import util.Coordinates;

public class RocketProjectileModel extends Projectile {
    public static final double ROCKET_SPEED = 4;
    public static final double ROCKET_WIDTH = 0.9;
    public static final double ROCKET_HEIGHT = 0.9;
    public static final int ROCKET_DAMAGE = 30;

    public static final int ROCKET_EXPLOSION_RADIUS = 2;
    public static final int ROCKET_EXPLOSION_DURATION = 1;

    public RocketProjectileModel(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, ROCKET_WIDTH, ROCKET_HEIGHT, ROCKET_DAMAGE, gameModel);
        movementHandler.setSpeed(ROCKET_SPEED);
        addCollisionListener(e -> {
            if (e.getSource() != source.getOwner()) {
                if (e.getInvolvedEntitiesList().stream()
                        .anyMatch(entity -> entity instanceof IVulnerableEntity vul
                        && vul != source.getOwner())) {
                                    explode();
                }
            }
        });
        addBlockedMovementListener(e -> explode());
    }

    private void explode() {
        despawn();
        gameModel.addEntity(new ExplosionZoneEntity(getPos(), ROCKET_EXPLOSION_RADIUS, ROCKET_EXPLOSION_RADIUS,
        ROCKET_DAMAGE, ROCKET_EXPLOSION_DURATION, gameModel));
    }

}
