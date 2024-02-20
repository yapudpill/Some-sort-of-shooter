package model.ingame.entity;

import model.ingame.weapon.ProjectileWeaponModel;

public interface ICombatEntity extends IMobileEntity, IVulnerableEntity{

    void attack();
    // TODO1: replace this with a more general interface that covers non projectile weapons
    ProjectileWeaponModel getWeapon();
    void setWeapon(ProjectileWeaponModel weapon);
}
