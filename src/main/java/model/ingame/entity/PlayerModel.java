package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.physics.MovementHandler;

public class PlayerModel extends CombatEntityModel {
    private static final double DEFAULT_SPEED = 5.3; // tile/s
    private static final double DASH_SPEED = 21;

    private final ModelTimer dashTimer;
    private final ModelTimer pickWeaponTimer;

    /*
     * tag interface for player actions, to be used by the controller (e.g. attack, reload, etc.)
     */
    @FunctionalInterface
    public interface PlayerAction {
        void performAction();
    }

    private boolean shouldPickWeapons = false;

    public PlayerModel(Coordinates pos, GameModel gameModel) {
        super(pos, 100, 0.5, 0.5, gameModel);

        dashTimer = new ModelTimer(0.5, false, () -> movementHandler.setSpeed(DEFAULT_SPEED), gameModel);
        pickWeaponTimer = new ModelTimer(0.5, false, () -> shouldPickWeapons = false, gameModel);

        movementHandler = new MovementHandler(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(DEFAULT_SPEED);
    }

    @Override
    public boolean shouldPickWeapons() {
        return shouldPickWeapons;
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

    public void pickWeapon() {
        this.pickWeaponTimer.start();
        this.shouldPickWeapons = true;
    }
}
