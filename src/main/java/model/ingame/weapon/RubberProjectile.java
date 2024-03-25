package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.physics.DamageListener;
import model.ingame.physics.RicochetListener;

public class RubberProjectile extends ProjectileModel {
    public static final double RUBBER_WIDTH = 0.3;
    public static final double RUBBER_HEIGHT = 0.3;
    public static final int RUBBER_DAMAGE = 20;
    public static final double RUBBER_SPEED = 0.05;



    public RubberProjectile(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, RUBBER_WIDTH, RUBBER_HEIGHT, RUBBER_DAMAGE, gameModel);
        addBlockedMovementListener(new RicochetListener());
        movementHandler.setSpeed(RUBBER_SPEED);
        addCollisionListener(new DamageListener(RUBBER_DAMAGE, e -> despawn()));
    }

}
