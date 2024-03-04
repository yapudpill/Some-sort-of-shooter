package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.CollisionEntityModel;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.physics.IMovementHandler;

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
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        if (target != sourceWeapon.getOwner()) {
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
        if(!this.isActive() || !this.getMovementHandler().isMoving())
        {
            gameModel.detachAsUpdateable(this);
            gameModel.removeEntity(this);
        }
    }

    @Override
    public ProjectileWeaponModel getSourceWeapon() {
        return sourceWeapon;
    }
}
