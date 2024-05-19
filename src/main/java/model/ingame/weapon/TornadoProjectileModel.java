package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.physics.DamageListener;
import util.Coordinates;

public class TornadoProjectileModel extends Projectile{
    public static final double TORNADO_SPEED = 8;
    public static final double TORNADO_WIDTH = 2.4;
    public static final double TORNADO_HEIGHT = 2.4;
    public static final int TORNADO_DAMAGE = 1;

    public TornadoProjectileModel(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, TORNADO_WIDTH, TORNADO_HEIGHT, TORNADO_DAMAGE, gameModel);
        movementHandler.setSpeed(TORNADO_SPEED);
        addCollisionListener(new DamageListener(TORNADO_DAMAGE));
        addBlockedMovementListener(e -> despawn());
    }
}
