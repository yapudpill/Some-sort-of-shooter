package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.physics.MovementHandlerModel;

public class BulletsModel extends ProjectileModel {
    public static final double BULLET_SPEED = 0.2;
    public static final double BULLET_WIDTH = 0.3;
    public static final double BULLET_HEIGHT = 0.3;
    public static final int BULLET_DAMAGE = 10;

    public BulletsModel(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, BULLET_WIDTH, BULLET_HEIGHT, BULLET_DAMAGE, gameModel);
        movementHandler.setSpeed(BULLET_SPEED);
    }
}
