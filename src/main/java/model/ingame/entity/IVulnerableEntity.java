package model.ingame.entity;

/**
 * Interface for entities that can take damage.
 */
public interface IVulnerableEntity extends IEntity {
    int getHealth();
    void setHealth(int health);
    void takeDamage(int damage);
    void takeDOT(int damage);
    int getMaxHealth();
    boolean isDead();

}
