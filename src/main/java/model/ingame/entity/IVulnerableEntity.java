package model.ingame.entity;

/**
 * Interface for entities that can take damage.
 */
public interface IVulnerableEntity extends IEntity {
    double getHealth();
    void setHealth(double health);
    void takeDamage(double damage);
    void takeDOT(double damage);
    double getMaxHealth();
    boolean isDead();

}
