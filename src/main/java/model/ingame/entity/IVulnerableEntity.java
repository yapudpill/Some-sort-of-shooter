package model.ingame.entity;

public interface IVulnerableEntity extends IEntity{
    int getHealth();
    void setHealth(int health);
    void takeDamage(int damage);
    int getMaxHealth();
    boolean isDead();

}
