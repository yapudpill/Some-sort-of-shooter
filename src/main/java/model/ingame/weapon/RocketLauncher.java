package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;

public class RocketLauncher extends ProjectileWeaponModel {
    private static final int COOL_DOWN = 2;

    public RocketLauncher(ICombatEntity owner, GameModel gameModel) {
        super("Rocket Launcher", "rocket_launcher", owner, gameModel, COOL_DOWN);
    }

    @Override
    public Projectile createProjectile() {
        return new RocketProjectileModel(getOwner().getPos(), this, gameModel);
    }
}
