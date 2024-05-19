package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;

/**
 * Model for the tornado gun weapon. It fires a tornado that pushes enemies away.
 */
public class TornadoGun extends ProjectileWeaponModel{
    public TornadoGun(ICombatEntity owner, GameModel gameModel) {
        super("TornadoGun", "tornado_gun", owner, gameModel, 1);
    }

    public Projectile createProjectile() {
        return new TornadoProjectileModel(owner.getPos(), this, gameModel);
    }
}
