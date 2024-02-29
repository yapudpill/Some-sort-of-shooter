package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;
import model.ingame.weapon.PistolModel;

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
      //  weapon = new PistolModel(this, physicsEngine);
    }

}
