package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.physics.DamageListener;
import model.ingame.physics.RicochetListener;
import util.Coordinates;

public class RubberProjectile extends Projectile {
    public static final double RUBBER_WIDTH = 0.2;
    public static final double RUBBER_HEIGHT = 0.2;
    public static final int RUBBER_DAMAGE = 20;
    public static final double RUBBER_SPEED = 0.1;
    private static final int MAX_BOUNCE_COUNT = 4;

    private int bounceCount = 0;

    public RubberProjectile(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, RUBBER_WIDTH, RUBBER_HEIGHT, RUBBER_DAMAGE, gameModel);
        movementHandler.setSpeed(RUBBER_SPEED);
        addBlockedMovementListener(new RicochetListener());
        addBlockedMovementListener(e -> increaseBounceCount());
        addCollisionListener(new DamageListener(RUBBER_DAMAGE, e -> despawn()));
    }

    public void increaseBounceCount() {
        bounceCount++;
        if (bounceCount >= MAX_BOUNCE_COUNT) {
            despawn();
        }
    }
}
