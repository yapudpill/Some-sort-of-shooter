package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.physics.MovementHandler;
import model.ingame.physics.SlidingListener;
import util.Coordinates;

/**
 * Represents a creature that can move and be attacked.
 */
public abstract class CreatureModel extends CollisionEntityModel implements IVulnerableEntity, IMobileEntity {
    protected MovementHandler movementHandler;
    protected int health;
    protected int maxHealth;

    public CreatureModel(Coordinates pos, int maxHealth, double width, double height, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        addBlockedMovementListener(new SlidingListener());
    }

    @Override
    public MovementHandler getMovementHandler() {
        return movementHandler;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (isDead()) {
            despawn();
            gameModel.stats.killedEnemies++;
        }
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
    public void update(double delta) {
        movementHandler.update(delta);
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth);
    }

    @Override
    public void despawn() {
        super.despawn();
        this.setHealth(0);
    }
}
