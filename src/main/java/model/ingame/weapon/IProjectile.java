package model.ingame.weapon;

import model.ingame.entity.IEffectEntity;
import model.ingame.entity.IMobileEntity;

public interface IProjectile extends IMobileEntity, IEffectEntity {

    boolean isActive();
    default boolean notActive() {
        return !isActive();
    }
    void setActive(boolean active);
    ProjectileWeaponModel getSourceWeapon();
}
