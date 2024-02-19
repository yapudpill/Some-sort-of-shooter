package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;

public class WalkingEnemyModel extends CreatureModel {
    private final PlayerModel player;
    public WalkingEnemyModel(PlayerModel player, PhysicsEngineModel engine) {
        super(50, 0.8, 0.8);
        this.player = player;
        movementHandler = new MovementHandlerModel<WalkingEnemyModel>(this, engine);
        movementHandler.setSpeed(0.03);

        addCollisionListener(e -> {
            if (e.getSource() != this) return;
            for (ICollisionEntity entity : e.getInvolvedEntitiesList()) {
                if (entity instanceof IVulnerableEntity vul) {
                    vul.takeDamage(10); //TODO: should not be hard coded
                }
            }
        });
    }

    @Override
    public void update() {
        Coordinates pPos = player.getPos();
        movementHandler.setDirectionVector(new Coordinates(pPos.x - pos.x, pPos.y - pos.y));
        super.update();
    }
}
