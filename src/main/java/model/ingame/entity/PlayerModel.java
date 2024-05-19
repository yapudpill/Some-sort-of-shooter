package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.physics.MovementHandler;
import model.ingame.weapon.FlameThrower;
import model.ingame.weapon.WeaponModel;
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
    WeaponModel otherWeapon;


    public PlayerModel(Coordinates pos, GameModel gameModel) {
        super(pos, MAX_HEALTH, 0.8, 0.8, gameModel,0);
        dashTimer = new ModelTimer(0.5, false, () -> movementHandler.setSpeed(DEFAULT_SPEED), gameModel);
        pickWeaponTimer = new ModelTimer(0.5, false, () -> {}, gameModel);

        movementHandler = new MovementHandler(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(DEFAULT_SPEED);
        setWeapon(new FlameThrower(this, gameModel));

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

    public void swap(){
        if (otherWeapon!= null){
            WeaponModel tmp = weapon;
            weapon = otherWeapon;
            otherWeapon = tmp;
        }
    }

    public void pickWeapon() {
        this.pickWeaponTimer.start();
    }

    public boolean isDashing() {
        return dashTimer.isRunning();
    }

    @Override
    public void setWeapon(WeaponModel weapon) {
        if (this.weapon != null && otherWeapon == null){
            otherWeapon = weapon;
            otherWeapon.setOwner(this);
        } else {
            this.weapon = weapon;
        }
    }

    public WeaponModel getOtherWeapon() {
        return otherWeapon;
    }

    public double getDashTimeLeft() {
        return dashTimer.getTimeLeft();
    }

    public double getDashDuration() {
        return dashTimer.getTimerDuration();
    }
}
