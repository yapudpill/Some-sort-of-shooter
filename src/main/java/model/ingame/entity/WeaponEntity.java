package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.weapon.WeaponConstructor;
import model.ingame.weapon.WeaponModel;
import util.Coordinates;

public class WeaponEntity extends CollisionEntityModel {
    public static EntityConstructor weaponEntityFactory(WeaponConstructor weaponFactory) {
        if (weaponFactory == null) return null;
        return (pos, gameModel) -> new WeaponEntity(pos, weaponFactory.createWeapon(null, gameModel), gameModel);
    }
    private final WeaponModel weapon;

    public WeaponEntity(Coordinates pos, WeaponModel weapon, GameModel gameModel) {
        super(pos, 0.5, 0.5, gameModel);
        this.weapon = weapon;
    }

    public WeaponModel getWeapon() {
        return weapon;
    }
}