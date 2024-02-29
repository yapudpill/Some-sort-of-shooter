package model.ingame.weapon;

import model.ingame.entity.IDamagingEntity;
import model.ingame.entity.IMobileEntity;

public interface IProjectile extends IMobileEntity, IDamagingEntity {

    boolean isActive();
    default boolean notActive() {
        return !isActive();
    }
    void setActive(boolean active);
    ProjectileWeaponModel getSourceWeapon();
}
