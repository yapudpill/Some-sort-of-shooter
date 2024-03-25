package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.CollisionEntityModel;
import model.ingame.entity.IEffectEntity;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.physics.IMovementHandler;
import model.ingame.physics.MovementHandlerModel;

public abstract class ProjectileModel extends CollisionEntityModel implements IProjectile {
    protected final ProjectileWeaponModel sourceWeapon;
    protected final int damage;
    protected IMovementHandler movementHandler;
    protected boolean active;

    public ProjectileModel(Coordinates pos, ProjectileWeaponModel source, double width, double height, int damage, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.damage = damage;
        this.sourceWeapon = source;
        this.active = true;
        this.movementHandler = new MovementHandlerModel<ProjectileModel>(this, gameModel.getPhysicsEngine());
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        boolean condition = true;
        if(sourceWeapon.getOwner() instanceof IEffectEntity effectEntity)
            condition = effectEntity.canApplyEffect(target);
        if (target != sourceWeapon.getOwner() && condition) {
            setActive(false);
            return true;
        }
        return false;
    }

    @Override
    public IMovementHandler getMovementHandler() {
        return movementHandler;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void update() {
        movementHandler.update();
    }

    @Override
    public ProjectileWeaponModel getSourceWeapon() {
        return sourceWeapon;
    }

}
