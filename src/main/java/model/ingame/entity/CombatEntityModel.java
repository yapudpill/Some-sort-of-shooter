package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.weapon.WeaponModel;
import util.Coordinates;

/**
 * Represents an entity that can move, attack and be attacked.
 */
public abstract class CombatEntityModel extends CreatureModel implements ICombatEntity {
    protected WeaponModel weapon;
    protected double damageMultiplier;

    public CombatEntityModel(Coordinates pos, double maxHealth, double width, double height, GameModel gameModel, double regen) {
        super(pos, maxHealth, width, height, gameModel, regen);
        damageMultiplier = 1;
        addCollisionListener(e -> {
            for (IEntity entity : e.getInvolvedEntitiesList()) {
                if (shouldPickWeapons() && entity instanceof WeaponEntity weaponEntity) {
                    if (weaponEntity.getWeapon() != null) {
                        setWeapon(weaponEntity.getWeapon());
                        weapon.setOwner(this);
                        weaponEntity.despawn();
                    }
                }
            }
        });
    }

    @Override
    public boolean attack() {
        if (weapon != null) {
            return weapon.attack();
        }
        return false;
    }

    @Override
    public boolean shouldPickWeapons() {
        return false;
    }

    @Override
    public WeaponModel getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(WeaponModel weapon) {
        this.weapon = weapon;
    }

    @Override
    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    @Override
    public void setDamageMultiplier(double damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }
}
