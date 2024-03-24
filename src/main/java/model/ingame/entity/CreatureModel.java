package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.physics.IMovementHandler;

public abstract class CreatureModel extends CollisionEntityModel implements IVulnerableEntity, IMobileEntity{
    protected IMovementHandler movementHandler;
    protected int health;
    protected int maxHealth;

    public CreatureModel(int maxHealth, double width, double height, GameModel gameModel) {
        super(Coordinates.ZERO, width, height, gameModel);
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
        if(isDead()) {
            despawn();
            System.out.println("Killed enemy");
            gameModel.getMapModel().printCollideables();
            gameModel.stats.killedEnemies++;
        }
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

}
