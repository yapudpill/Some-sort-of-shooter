package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.weapon.RubberWeapon;
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
        dashTimer = new ModelTimer(30, () -> {
            dashing = false;
            movementHandler.setSpeed(0.09);
        }, gameModel);
        dashTimer.setRepeats(false);
        movementHandler = new MovementHandlerModel<PlayerModel>(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(0.09);
        setWeapon(new RubberWeapon(this, gameModel));
    }

    public void update(){
        super.update();
    }

    @Override
    public boolean shouldPickWeapons() {
        return true;
    }


    public void dash(){
        if(dashing) return;
        dashing = true;
        movementHandler.setSpeed(0.35);
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

}
