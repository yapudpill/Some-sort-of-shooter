package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.CollisionEntityModel;
import model.ingame.entity.IEffectEntity;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.physics.MovementHandler;
import util.Coordinates;

public abstract class ProjectileModel extends CollisionEntityModel implements IProjectile {
    protected final ProjectileWeaponModel sourceWeapon;
    protected final int damage;
    protected final MovementHandler movementHandler;
    protected boolean active;

    public ProjectileModel(Coordinates pos, ProjectileWeaponModel source, double width, double height, int damage, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.damage = damage;
        this.sourceWeapon = source;
        this.movementHandler = new MovementHandler(this, gameModel.getPhysicsEngine());
        this.active = true;
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        boolean condition = true;
        if(sourceWeapon.getOwner() instanceof IEffectEntity effectEntity)
            condition = effectEntity.canApplyEffect(target);
        if (target != sourceWeapon.getOwner() && condition) {
            return true;
        }
        return false;
    }

    @Override
    public MovementHandler getMovementHandler() {
        return movementHandler;
    }

    @Override
    public void update(double delta) {
        movementHandler.update(delta);
    }
}
