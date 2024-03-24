package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.physics.MovementHandlerModel;
import util.ModelTimer;

public class PlayerModel extends CombatEntityModel {
    private boolean dashing = false;
    private ModelTimer dashTimer;
    /*
     * tag interface for player actions, to be used by the controller (e.g. attack, reload, etc.)
     */

    @FunctionalInterface
    public interface PlayerAction {
        void performAction();
    }

    public PlayerModel(GameModel gameModel) {
        super(100, 0.5, 0.5, gameModel);
        dashTimer = new ModelTimer(30, () -> dashing = false, gameModel);
        dashTimer.setRepeats(false);
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
        return true;
    }

    public void dash(){
        if(dashing) return;
        dashing = true;
        dashTimer.start();
    }

}
