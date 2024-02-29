package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.CollisionEntityModel;
import model.ingame.physics.IMovementHandler;

public abstract class ProjectileModel extends CollisionEntityModel implements IProjectile{
    protected ProjectileWeaponModel sourceWeapon;
    protected int damage;
    protected boolean active;
    protected IMovementHandler movementHandler;

    public ProjectileModel(Coordinates pos, double width, double height, int damage, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.damage = damage;
        this.active = true;
    }

    @Override
    public int getDamage() {
        return damage;
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
    public boolean notActive(){
        return !active;
    }

    @Override
    public void setSourceWeapon(ProjectileWeaponModel sourceWeapon) {
        this.sourceWeapon = sourceWeapon;
    }

    @Override
    public ProjectileWeaponModel getSourceWeapon() {
        return sourceWeapon;
    }
}
