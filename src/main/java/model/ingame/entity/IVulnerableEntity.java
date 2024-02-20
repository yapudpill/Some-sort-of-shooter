package model.ingame.entity;

public interface IVulnerableEntity extends IEntity{
    
    int getHealth();
    void takeDamage(int damage);
    int getMaxHealth();

}
