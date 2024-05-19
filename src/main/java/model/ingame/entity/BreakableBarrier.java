package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.weapon.Projectile;
import util.Coordinates;

import java.util.function.Predicate;

/**
 * A breakable barrier that can be destroyed. Represented by an entity instead of a tile, as the tiles are not as dynamic.
 */
public class BreakableBarrier extends CollisionEntityModel implements IVulnerableEntity {
    private int health;
    private final int maxHealth;
    private Predicate<IEntity> blockingCondition = e -> e instanceof Projectile;

    public BreakableBarrier(Coordinates pos, GameModel gameModel) {
        super(pos, 1, 1, gameModel);
        this.health = 100;
        this.maxHealth = 100;
        gameModel.getMapModel().getTile(pos).addCanEnterCondition(blockingCondition);
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            despawn();
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
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public void despawn() {
        super.despawn();
        gameModel.getMapModel().getTile(pos).removeCanEnterCondition(blockingCondition);
    }
}
