package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.physics.DamageListener;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;

public class WalkingEnemyModel extends CreatureModel implements IEffectEntity {
    private final PlayerModel player;
    public WalkingEnemyModel(PlayerModel player, PhysicsEngineModel engine) {
        super(50, 0.8, 0.8);
        this.player = player;
        movementHandler = new MovementHandlerModel<WalkingEnemyModel>(this, engine);
        movementHandler.setSpeed(0.03);
        addCollisionListener(new DamageListener(10)); //TODO: damages should not be hard coded
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        return target instanceof PlayerModel;
    }

    @Override
    public void update() {
        Coordinates pPos = player.getPos();
        movementHandler.setDirectionVector(new Coordinates(pPos.x - pos.x, pPos.y - pos.y));
        super.update();
    }
}
