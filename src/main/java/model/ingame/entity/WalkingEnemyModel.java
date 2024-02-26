package model.ingame.entity;

import java.util.Random;

import model.ingame.Coordinates;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;

public class WalkingEnemyModel extends CreatureModel {
    private final PlayerModel player;
    Random rng = new Random();

    public WalkingEnemyModel(PlayerModel player, PhysicsEngineModel engine, Coordinates pos) {
        super(50, 0.8, 0.8);
        this.player = player;
        movementHandler = new MovementHandlerModel<WalkingEnemyModel>(this, engine);
        movementHandler.setSpeed(0.03);
        this.pos = pos;

        addCollisionListener(e -> {
            if (e.getSource() != this) return;
            for (ICollisionEntity entity : e.getInvolvedEntitiesList()) {
                if (entity instanceof PlayerModel p) {
                    p.takeDamage(10); //TODO: should not be hard coded
                }
            }
        });
    }

    @Override
    public void update() {
        Coordinates pPos = player.getPos();
        movementHandler.setDirectionVector(new Coordinates(pPos.x - pos.x   , pPos.y - pos.y ));
        super.update();
    }
}
