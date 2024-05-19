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
    protected int health;
    protected int maxHealth;
    protected int regen;
    protected ModelTimer damage_over_time;

    public CreatureModel(Coordinates pos, int maxHealth, double width, double height, GameModel gameModel, int regen) {
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
    public void takeDamage(int damage) {
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

    public void takeDOT(int damage){
        regen -= damage;
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
