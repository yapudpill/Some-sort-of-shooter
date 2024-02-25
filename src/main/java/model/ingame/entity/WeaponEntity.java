package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.weapon.ProjectileWeaponModel;

public class WeaponEntity extends CollisionEntityModel {
    private final ProjectileWeaponModel weapon;

    public WeaponEntity(Coordinates pos, ProjectileWeaponModel weapon) {
        super(pos, 0.5, 0.5);
        this.weapon = weapon;
    }

    public ProjectileWeaponModel getWeapon() {
        return weapon;
    }
}
