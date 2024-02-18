package model.ingame.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.ingame.Coordinates;
import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;
import model.ingame.physics.IMovementHandler;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;
import model.ingame.weapon.BulletsModel;
import model.ingame.weapon.IProjectile;
import model.ingame.weapon.PistolModel;
import model.ingame.weapon.ProjectileWeaponModel;

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
        weapon = new PistolModel(this, physicsEngine);
    }

}
