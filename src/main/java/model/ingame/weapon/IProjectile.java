package model.ingame.weapon;

import model.ingame.entity.IMobileEntity;

public interface IProjectile extends IMobileEntity {

    boolean isActive();
    boolean notActive();
    void setActive(boolean active);
    int getDamage();
    void setSourceWeapon(ProjectileWeaponModel sourceWeapon);
    ProjectileWeaponModel getSourceWeapon();

}
