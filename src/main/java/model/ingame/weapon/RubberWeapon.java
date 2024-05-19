package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.ICombatEntity;

/**
 * Rubber gun weapon model. Fires rubber projectiles.
 */
public class RubberWeapon extends ProjectileWeaponModel {
    private int shotFired;
    ModelTimer inBetweenTimer;

    public RubberWeapon(ICombatEntity owner, GameModel gameModel) {
        super("Rubber Gun", "rubbergun", owner, gameModel, 1.92);
        this.shotFired = 0;
        inBetweenTimer = new ModelTimer(0.16, false, this::fire, gameModel);
    }

    @Override
    public Projectile createProjectile() {
        return new RubberProjectile(owner.getPos(), this, gameModel);
    }

    @Override
    public boolean attack() {
        if (!isCoolingDown()) {
            coolDownTimer.start();
            fire();
        }
        return false;
    }

    @Override
    public void fire() {
        super.fire();
        shotFired++;
        if (shotFired <= 3) {
            inBetweenTimer.start();
        } else {
            shotFired = 0;
        }
    }
}
