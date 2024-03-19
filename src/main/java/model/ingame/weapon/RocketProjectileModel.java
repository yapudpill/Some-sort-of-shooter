package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.ExplosionZoneEntity;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IVulnerableEntity;
import util.ModelTimer;

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
            // FIXME: it's really not practical to send events twice, as this makes these kind of checks necessary
            boolean explode = false;
            if (e.getSource() != source.getOwner() && !(e.getSource() instanceof ExplosionZoneEntity)) {
                if (e.getInvolvedEntitiesList().stream().anyMatch(entity -> entity instanceof IVulnerableEntity vulnerableEntity && vulnerableEntity != source.getOwner())) {
                    explode = true;
                    despawn();
                    gameModel.addEntity(new ExplosionZoneEntity(getPos(), ROCKET_EXPLOSION_RADIUS, ROCKET_EXPLOSION_RADIUS, ROCKET_DAMAGE, ROCKET_EXPLOSION_DURATION, gameModel));
                }
            }
        });
    }
}
