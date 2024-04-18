package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.physics.MovementHandler;

public abstract class CreatureModel extends CollisionEntityModel implements IVulnerableEntity, IMobileEntity {
    protected MovementHandler movementHandler;
    protected int health;
    protected int maxHealth;

    public CreatureModel(Coordinates pos, int maxHealth, double width, double height, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @Override
    public MovementHandler getMovementHandler() {
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
    public void update(double delta) {
        if(isDead()) {
            gameModel.detachAsUpdateable(this);
            gameModel.removeEntity(this);
            gameModel.getMapModel().removeCollidableAt(this, (int) pos.x, (int) pos.y);
            gameModel.stats.killedEnemies++;
        }
        movementHandler.update(delta);
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }
}
