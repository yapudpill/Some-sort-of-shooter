package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.physics.MovementHandlerModel;
import util.ModelTimer;

public class PlayerModel extends CombatEntityModel {
    private boolean dashing = false;
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

        dashTimer = new ModelTimer(30, () -> dashing = false, gameModel);
        dashTimer.setRepeats(false);

        pickWeaponTimer = new ModelTimer(30, () -> shouldPickWeapons = false, gameModel);

        movementHandler = new MovementHandlerModel<PlayerModel>(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(0.09);
    }

    public void update(){
        if(dashing){
            movementHandler.setSpeed(0.35);
        }
        else movementHandler.setSpeed(0.09);
        super.update();
    }

    @Override
    public boolean shouldPickWeapons() {
        return shouldPickWeapons;
    }


    public void dash(){
        if(dashing) return;
        dashing = true;
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
