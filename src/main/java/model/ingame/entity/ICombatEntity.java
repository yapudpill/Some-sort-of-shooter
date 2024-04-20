package model.ingame.entity;

import model.ingame.weapon.WeaponModel;

public interface ICombatEntity extends IMobileEntity, IVulnerableEntity {
    boolean attack();
    boolean shouldPickWeapons();
    WeaponModel getWeapon();
    void setWeapon(WeaponModel weapon);
}
