package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.weapon.WeaponModel;


public abstract class CombatEntityModel extends CreatureModel implements ICombatEntity {
    protected WeaponModel weapon;

    public CombatEntityModel(int maxHealth, double width, double height, GameModel gameModel) {
        super(maxHealth, width, height, gameModel);
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

    public boolean shouldPickWeapons() {
        return false;
    }

    @Override
    public boolean attack() {
        if (weapon != null) {
            return weapon.attack();
        }
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
    public void update() {
        super.update();
    }

}
