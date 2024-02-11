package model.ingame.entity;

public interface IVulnerableEntity {
    
    int getHealth();
    void takeDamage(int damage);
    int getMaxHealth();

}
