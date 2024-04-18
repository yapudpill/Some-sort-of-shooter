package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.physics.IMovementHandler;
import model.ingame.physics.SlidingListener;

public abstract class CreatureModel extends CollisionEntityModel implements IVulnerableEntity, IMobileEntity{
    protected IMovementHandler movementHandler;
    protected int health;
    protected int maxHealth;

    public CreatureModel(Coordinates pos, int maxHealth, double width, double height, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        addBlockedMovementListener(new SlidingListener());
    }

    @Override
    public IMovementHandler getMovementHandler() {
        return movementHandler;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if(isDead()) {
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
    public void update(){
        movementHandler.update();
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public void setHealth(int health) {
        if(health > maxHealth) this.health = maxHealth;
        else this.health = health;
    }

    public void reset(){
        health = maxHealth;
    }

    @Override
    public void despawn() {
        super.despawn();
        this.setHealth(0);
    }
}
