package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.weapon.GravityGun;
import util.Coordinates;

/**
 * Model for the player entity.
 */
public class PlayerModel extends CombatEntityModel {
    private static final double DEFAULT_SPEED = 5.3; // tile/s
    private static final double DASH_SPEED = 21;
    private static final int MAX_HEALTH = 100;

    private final ModelTimer dashTimer;
    private final ModelTimer pickWeaponTimer;


    public PlayerModel(Coordinates pos, GameModel gameModel) {
        super(pos,DEFAULT_SPEED ,MAX_HEALTH, 0.8, 0.8, gameModel);
        dashTimer = new ModelTimer(0.5, false, () -> movementHandler.setSpeed(DEFAULT_SPEED), gameModel);
        pickWeaponTimer = new ModelTimer(0.5, false, () -> {}, gameModel);
        setWeapon(new GravityGun(this, gameModel));
    }

    @Override
    public boolean shouldPickWeapons() {
        return pickWeaponTimer.isRunning();
    }

    public void dash() {
        movementHandler.setSpeed(DASH_SPEED);
        dashTimer.start();
    }

    @Override
    public boolean attack() {
        if (super.attack()) {
            gameModel.stats.nbAttacks++;
            return true;
        }
        return false;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        if (isDead()) {
            despawn();
            gameModel.setRunning(false);
        }
    }

    public void pickWeapon() {
        this.pickWeaponTimer.start();
    }

    public boolean isDashing() {
        return dashTimer.isRunning();
    }

    public double getDashTimeLeft() {
        return dashTimer.getTimeLeft();
    }

    public double getDashDuration() {
        return dashTimer.getTimerDuration();
    }
}
