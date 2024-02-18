package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.physics.IMovementHandler;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;
import model.ingame.weapon.BulletsModel;
import model.ingame.weapon.PistolModel;
import model.ingame.weapon.ProjectileWeaponModel;

public abstract class CreatureModel extends CollisionEntityModel implements IVulnerableEntity, IMobileEntity{
    protected IMovementHandler movementHandler;
    protected int health;
    protected int maxHealth;

    public CreatureModel(int maxHealth, double width, double height) {
        super(Coordinates.ZERO, width, height);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @Override
    public IMovementHandler getMovementHandler() {
        return movementHandler;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }
    
    @Override
    public void update(){
        movementHandler.update();
    }
}
