package model.ingame.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.MovementHandlerModel;
import model.level.MapModel;

public class WalkingEnemyModel extends CreatureModel {
    private final PlayerModel player;
    private static FloodFillPathFinder pathFinder;

    public WalkingEnemyModel(Coordinates pos, GameModel gameModel) {
        super(50, 0.8, 0.8, gameModel);
        this.player = gameModel.getPlayer();
        movementHandler = new MovementHandlerModel<WalkingEnemyModel>(this, gameModel.getPhysicsEngine());
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

    public static void setPathFinder(FloodFillPathFinder pathFinder) {
        WalkingEnemyModel.pathFinder = pathFinder;
    }

    @Override
    public void update() {
        pathFinder.setTarget(player.getPos());
        if(!pathFinder.isRunning()) pathFinder.start();
        Coordinates lowestCoord = pathFinder.getLowestNodeAround((int) pos.x, (int) pos.y);
        if(pos.isInCenter() || !movementHandler.isMoving()) movementHandler.setDirectionVector(new Coordinates( lowestCoord.x - pos.x, lowestCoord.y - pos.y));
        super.update();
    }

}
