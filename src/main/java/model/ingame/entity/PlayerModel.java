package model.ingame.entity;

import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;

public class PlayerModel extends CombatEntityModel {
    /*
     * tag interface for player actions, to be used by the controller (e.g. attack, reload, etc.)
     */

    @FunctionalInterface
    public interface PlayerAction {
        void performAction();
    }

    public PlayerModel(PhysicsEngineModel physicsEngine) {
        super(100, 0.5, 0.5);
        movementHandler = new MovementHandlerModel<PlayerModel>(this, physicsEngine);
        movementHandler.setSpeed(0.09);
    }

}
