package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;

public class BulletsModel extends ProjectileModel {
    public static final double BULLET_SPEED = 0.01;
    public static final double BULLET_WIDTH = 0.3;
    public static final double BULLET_HEIGHT = 0.3;
    public static final int BULLET_DAMAGE = 1;

    public BulletsModel(Coordinates pos, ProjectileWeaponModel source, PhysicsEngineModel physicsEngine) {
        super(pos, source, BULLET_WIDTH, BULLET_HEIGHT, BULLET_DAMAGE);
        this.movementHandler = new MovementHandlerModel<BulletsModel>(this, physicsEngine);
        movementHandler.setSpeed(BULLET_SPEED);
    }
}
