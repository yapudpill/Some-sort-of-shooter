package model.ingame.weapon;

import model.ingame.IUpdateable;
import model.ingame.entity.IMobileEntity;

public interface IProjectile extends IMobileEntity {
    
    boolean isActive();
    void setActive(boolean active);
    int getDamage();

}
