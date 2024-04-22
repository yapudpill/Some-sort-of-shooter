package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.physics.MovementHandler;
import model.ingame.weapon.RocketLauncher;

public class PlayerModel extends CombatEntityModel {
    private static final double DEFAULT_SPEED = 5.3; // tile/s
    private static final double DASH_SPEED = 21;
    private static final int MAX_HEALTH = 100;

    private final ModelTimer dashTimer;
    private final ModelTimer pickWeaponTimer;

    /**
     * tag interface for player actions, to be used by the controller (e.g. attack, reload, etc.)
     */
    @FunctionalInterface
    public interface PlayerAction {
        void performAction();
    }

    public PlayerModel(Coordinates pos, GameModel gameModel) {
        super(pos,MAX_HEALTH, 0.5, 0.5, gameModel);
        dashTimer = new ModelTimer(0.5, false, () -> movementHandler.setSpeed(DEFAULT_SPEED), gameModel);
        pickWeaponTimer = new ModelTimer(0.5, false, () -> {}, gameModel);

        movementHandler = new MovementHandler(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(DEFAULT_SPEED);
        setWeapon(new RocketLauncher(this, gameModel));
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
    public void takeDamage(int damage){
        health-=damage;
        if(isDead()){
            despawn();
            gameModel.setRunning(false);
        }
    }

    public void pickWeapon() {
        this.pickWeaponTimer.start();
    }
}
