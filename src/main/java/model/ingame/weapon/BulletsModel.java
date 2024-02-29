package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.IEntity;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;

public class BulletsModel extends ProjectileModel{
    public static final double BULLET_SPEED = 0.02;
    public static final double BULLET_WIDTH = 0.3;
    public static final double BULLET_HEIGHT = 0.3;
    public static final int BULLET_DAMAGE = 10;

    public BulletsModel(Coordinates pos, GameModel gameModel) {
        super(pos, BULLET_WIDTH, BULLET_HEIGHT, BULLET_DAMAGE, gameModel);
        this.movementHandler = new MovementHandlerModel<BulletsModel>(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(BULLET_SPEED);
        addCollisionListener((e) -> {
            IEntity source = this.getSourceWeapon().getOwner();
            if(e.getSource() != this) return;
            e.getInvolvedEntitiesList().forEach((entity) -> {
                if(entity instanceof IVulnerableEntity v && entity != source && !v.isDead()) {
                    ((IVulnerableEntity) entity).takeDamage(this.getDamage());
                    this.setActive(false);
                }
            });
        });
    }


}
