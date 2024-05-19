package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;

/**
 * Rubber gun weapon model. Fires rubber projectiles.
 */
public class RubberWeapon extends ProjectileWeaponModel {

    public RubberWeapon(ICombatEntity owner, GameModel gameModel) {
        super("Rubber Gun", "rubbergun", gameModel, owner, 1);
    }

    @Override
    public Projectile createProjectile() {
        return new RubberProjectile(owner.getPos(), this, gameModel);
    }

}
