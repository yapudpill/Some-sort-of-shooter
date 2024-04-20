package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;

public class BulletsModel extends ProjectileModel {
    public static final double BULLET_SPEED = 12;
    public static final double BULLET_WIDTH = 0.3;
    public static final double BULLET_HEIGHT = 0.3;
    public static final int BULLET_DAMAGE = 12;

    public BulletsModel(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, BULLET_WIDTH, BULLET_HEIGHT, BULLET_DAMAGE, gameModel);
        movementHandler.setSpeed(BULLET_SPEED);
        addBlockedMovementListener(e -> despawn());
    }
}
