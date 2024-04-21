package model.ingame.weapon;

import model.ingame.GameModel;
import util.Coordinates;

class BulletsModel extends Projectile {
    private static final double BULLET_SPEED = 12;
    private static final double BULLET_WIDTH = 0.3;
    private static final double BULLET_HEIGHT = 0.3;
    private static final int BULLET_DAMAGE = 12;

    BulletsModel(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, BULLET_WIDTH, BULLET_HEIGHT, BULLET_DAMAGE, gameModel);
        movementHandler.setSpeed(BULLET_SPEED);
        addBlockedMovementListener(e -> despawn());
    }
}
