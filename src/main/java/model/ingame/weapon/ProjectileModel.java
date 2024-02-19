package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.entity.CollisionEntityModel;
import model.ingame.entity.IMobileEntity;
import model.ingame.physics.IMovementHandler;
import model.ingame.physics.PhysicsEngineModel;

public abstract class ProjectileModel extends CollisionEntityModel implements IProjectile{
    protected ProjectileWeaponModel sourceWeapon;
    protected int damage;
    protected boolean active;
    protected IMovementHandler movementHandler;

    public ProjectileModel(Coordinates pos, double width, double height, int damage) {
        super(pos, width, height);
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
