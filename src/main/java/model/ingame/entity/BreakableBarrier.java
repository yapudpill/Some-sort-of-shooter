package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.weapon.Projectile;
import util.Coordinates;

import java.util.function.Predicate;

/**
 * A breakable barrier that can be destroyed. Represented by an entity instead of a tile, as the tiles are not as dynamic.
 */
public class BreakableBarrier extends CollisionEntityModel implements IVulnerableEntity {
    private double health;
    private final double maxHealth;
    private Predicate<IEntity> blockingCondition = e -> e instanceof Projectile;

    public BreakableBarrier(Coordinates pos, GameModel gameModel) {
        super(pos, 1, 1, gameModel);
        this.health = 100;
        this.maxHealth = 100;
        gameModel.getMapModel().getTile(pos).addCanEnterCondition(blockingCondition);
    }

    @Override
    public void takeDamage(double damage) {
        health -= damage;
        if (health <= 0) {
            despawn();
        }
    }

    @Override
    public void takeDOT(double damage) {}

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setHealth(double health) {
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
