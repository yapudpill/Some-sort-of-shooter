package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.physics.BlockedMovementEvent;
import model.ingame.physics.MovementHandlerModel;

public class PlayerModel extends CombatEntityModel {
    /*
     * tag interface for player actions, to be used by the controller (e.g. attack, reload, etc.)
     */

    @FunctionalInterface
    public interface PlayerAction {
        void performAction();
    }

    public PlayerModel(GameModel gameModel) {
        super(100, 0.5, 0.5, gameModel);
        movementHandler = new MovementHandlerModel<PlayerModel>(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(0.09);
        addBlockedMovementListener(System.out::println);
    }

    @Override
    public boolean shouldPickWeapons() {
        return true;
    }

    @Override
    public boolean attack() {
        if (super.attack()) {
            gameModel.stats.nbAttacks++;
            return true;
        }
        return false;
    }
}
