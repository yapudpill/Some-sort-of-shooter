package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.physics.MovementHandler;
import model.ingame.physics.SlidingListener;
import util.Coordinates;

/**
 * Represents a creature that can move and be attacked.
 */
public abstract class CreatureModel extends CollisionEntityModel implements IVulnerableEntity, IMobileEntity {
    protected MovementHandler movementHandler;
    protected double health;
    protected double maxHealth;
    protected double regen;
    protected ModelTimer damage_over_time;

    public CreatureModel(Coordinates pos, double maxHealth, double width, double height, GameModel gameModel, double regen) {
        super(pos, width, height, gameModel);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        addBlockedMovementListener(new SlidingListener());
        this.regen = regen;
        damage_over_time = new ModelTimer(1,true, this::heal,gameModel);
        damage_over_time.start();
    }

    @Override
    public MovementHandler getMovementHandler() {
        return movementHandler;
    }

    @Override
    public void takeDamage(double damage) {
        health -= damage;
        if (isDead() && gameModel.getEntitySet().contains(this)) {
            despawn();
            gameModel.stats.killedEnemies++;
        }
    }

    public void heal(){
        takeDamage(-regen);
        if (health > maxHealth) health = maxHealth;
    }

    public void takeDOT(double damage){
        regen -= damage;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public double getMaxHealth() {
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
    public void setHealth(double health) {
        this.health = Math.min(health, maxHealth);
    }

    @Override
    public void despawn() {
        super.despawn();
        this.setHealth(0);
    }
}
