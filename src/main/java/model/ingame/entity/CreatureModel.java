package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.physics.IMovementHandler;

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
        if(isDead()) return;
        movementHandler.update();
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }
}
