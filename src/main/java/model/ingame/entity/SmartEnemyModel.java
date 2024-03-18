package model.ingame.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.MovementHandlerModel;
import model.level.MapModel;

public class SmartEnemyModel extends CreatureModel {
    private final PlayerModel player;
    private static FloodFillPathFinder pathFinder;
    private static int attackDirection = 0;

    public SmartEnemyModel(Coordinates pos, GameModel gameModel) {
        super(50, 0.8, 0.8, gameModel);
        this.player = gameModel.getPlayer();
        movementHandler = new MovementHandlerModel<SmartEnemyModel>(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(0.06);
        this.pos = pos;
        attackDirection = (attackDirection + 1) % 4;

        addCollisionListener(e -> {
            if (e.getSource() != this) return;
            for (ICollisionEntity entity : e.getInvolvedEntitiesList()) {
                if (entity instanceof PlayerModel p) {
                    p.takeDamage(20); //TODO: should not be hard coded
                }
            }
        });
    }

    public static void setPathFinder(FloodFillPathFinder pathFinder) {
        SmartEnemyModel.pathFinder = pathFinder;
        pathFinder.reachTarget(false);
    }

    @Override
    public void update() {
        pathFinder.setTargets(getCurrentTargetTiles());
        if(!pathFinder.isRunning()) pathFinder.start();
        Coordinates lowestCoord = pathFinder.getLowestNodeAround((int) pos.x, (int) pos.y);
        if(pos.isInCenter() || !movementHandler.isMoving()) movementHandler.setDirectionVector(new Coordinates( lowestCoord.x - pos.x, lowestCoord.y - pos.y));
        super.update();
    }

    public List<Coordinates> getCurrentTargetTiles() {
        // attack from the top, meaning the tile below are forbidden
        Coordinates playerPos = player.getPos();
        MapModel mapModel = gameModel.getMapModel();
        List<Coordinates> res = new ArrayList<>();
        switch(attackDirection){
            case 0 :
                if(!mapModel.isOutOfBounds((int) playerPos.x, (int) playerPos.y +1)){
                    for(int i = 0; i < 3; i++){
                        if(mapModel.isWalkableAt((int) playerPos.x - 1 + i, (int) playerPos.y + 1)){
                            res.add(new Coordinates((int) playerPos.x - 1 + i, (int) playerPos.y + 1));
                            break;
                        }
                    }
                }
                break;
            case 1:
                if(!mapModel.isOutOfBounds((int) playerPos.x - 1, (int) playerPos.y)){
                    for(int i = 0; i < 3; i++){
                        if(mapModel.isWalkableAt((int) playerPos.x - 1, (int) playerPos.y - 1 + i)){
                            res.add(new Coordinates((int) playerPos.x - 1, (int) playerPos.y - 1 + i));
                            break;
                        }
                    }
                }
                break;
            case 2:
                if(!mapModel.isOutOfBounds((int) playerPos.x, (int) playerPos.y - 1)){
                    for(int i = 0; i < 3; i++){
                        if(mapModel.isWalkableAt((int) playerPos.x - 1 + i, (int) playerPos.y - 1)){
                            res.add(new Coordinates((int) playerPos.x - 1 + i, (int) playerPos.y - 1));
                            break;
                        }
                    }
                }
                break;
            case 3:
                if(!mapModel.isOutOfBounds((int) playerPos.x + 1, (int) playerPos.y)){
                    for(int i = 0; i < 3; i++){
                        if(mapModel.isWalkableAt((int) playerPos.x + 1, (int) playerPos.y - 1 + i)){
                            res.add(new Coordinates((int) playerPos.x + 1, (int) playerPos.y - 1 + i));
                            break;
                        }
                    }

                }
                break;
        }
        if(res.isEmpty()) res.add(playerPos);
        return res;
    }
}
