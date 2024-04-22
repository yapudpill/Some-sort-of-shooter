package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.weapon.WeaponModel;
import model.ingame.weapon.WeaponModel.IWeaponFactory;

public class WeaponEntity extends CollisionEntityModel {
    public static IEntityFactory weaponEntityFactory(IWeaponFactory weaponFactory) {
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