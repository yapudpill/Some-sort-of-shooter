package model.ingame.entity;

import model.ingame.weapon.WeaponModel;

public interface ICombatEntity extends IMobileEntity, IVulnerableEntity{

    void attack();
    WeaponModel getWeapon();
    void setWeapon(WeaponModel weapon);
}
