package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.weapon.WeaponModel;
import util.Coordinates;

public class WeaponEntity extends CollisionEntityModel {
    private final WeaponModel weapon;

    public WeaponEntity(Coordinates pos, WeaponModel weapon, GameModel gameModel) {
        super(pos, 0.5, 0.5, gameModel);
        this.weapon = weapon;
    }

    public WeaponModel getWeapon() {
        return weapon;
    }
}