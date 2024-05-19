package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.physics.MovementHandler;
import model.ingame.weapon.KnifeWeapon;
import model.ingame.weapon.WeaponModel;
import util.Coordinates;

/**
 * Model for the player entity.
 */
public class PlayerModel extends CombatEntityModel {
    private static final double DEFAULT_SPEED = 5.3; // tile/s
    private static final double DASH_SPEED = 21;

    private final ModelTimer dashTimer;
    private final ModelTimer pickWeaponTimer;
    WeaponModel otherWeapon;


    public PlayerModel(Coordinates pos, GameModel gameModel) {
        super(pos, DEFAULT_SPEED, 100, 0.8, 0.8, gameModel,0);
        dashTimer = new ModelTimer(0.5, false, () -> movementHandler.setSpeed(DEFAULT_SPEED), gameModel);
        pickWeaponTimer = new ModelTimer(0.5, false, () -> {}, gameModel);

        setWeapon(new KnifeWeapon(this, gameModel));
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
    public void takeDamage(double damage) {
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

    public void upgradeRandom(){
        int i = (int) (Math.random() * 4);
        switch (i){
            case 0:
                upgradeDamage();
                break;
            case 1:
                upgradeHealth();
                break;
            case 2:
                upgradeRegen();
                break;
            case 3:
                upgradeSpeed();
                break;
        }
    }

    public void upgradeRegen(){
        regen+=0.2;
        gameModel.stats.nbRegenUpgrade++;
    }
    public void upgradeSpeed(){
        movementHandler.setSpeed(DEFAULT_SPEED+0.5);
        gameModel.stats.nbSpeedUpgrade++;
    }
    public void upgradeDamage(){
        damageMultiplier+=0.2;
        gameModel.stats.nbDamageUpgrade++;
    }
    public void upgradeHealth(){
        health+=20;
        gameModel.stats.nbHealthUpgrade++;
    }


    public double getDashTimeLeft() {
        return dashTimer.getTimeLeft();
    }

    public double getDashDuration() {
        return dashTimer.getTimerDuration();
    }
}
