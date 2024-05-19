package model.ingame.entity;

import model.ingame.weapon.WeaponModel;

/**
 * Interface for entities that can attack other entities.
 * <p>
 * Both the interface and the abstract class are necessary because java doesn't support multiple inheritance, and some
 * components may be an ICollisionEntity without extending {@link CombatEntityModel}.
 */
public interface ICombatEntity extends IMobileEntity, IVulnerableEntity {
    boolean attack();

    boolean shouldPickWeapons();

    WeaponModel getWeapon();

    void setWeapon(WeaponModel weapon);
}
