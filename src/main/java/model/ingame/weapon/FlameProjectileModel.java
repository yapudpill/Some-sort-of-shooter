package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.physics.DamageListener;
import model.ingame.physics.DamageOverTimeListener;
import util.Coordinates;

public class FlameProjectileModel extends Projectile{
    public static final double FLAME_SPEED = 5;
    public static final double FLAME_WIDTH = 0.5;
    public static final double FLAME_HEIGHT = 0.5;
    public static final int FLAME_DAMAGE = 2;
    public static final int FLAME_DOT = 1;

    public FlameProjectileModel(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, FLAME_WIDTH, FLAME_HEIGHT, FLAME_DAMAGE, gameModel);
        movementHandler.setSpeed(FLAME_SPEED);
        addCollisionListener(new DamageListener(FLAME_DAMAGE));
        addCollisionListener(new DamageOverTimeListener(FLAME_DOT, e -> despawn()));
        addBlockedMovementListener(e -> despawn());
    }
}
