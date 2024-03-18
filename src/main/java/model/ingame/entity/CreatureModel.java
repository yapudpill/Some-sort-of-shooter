package model.ingame.entity;

import java.util.ArrayList;
import java.util.List;

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
        if(isDead()){
            gameModel.detachAsUpdateable(this);
            gameModel.removeEntity(this);
        }
        movementHandler.update();
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    public void reset(){
        health = maxHealth;
    }

}
