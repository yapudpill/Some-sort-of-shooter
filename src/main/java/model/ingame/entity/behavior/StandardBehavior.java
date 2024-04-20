package model.ingame.entity.behavior;

import model.ingame.entity.IEntity;
import model.ingame.entity.IMobileEntity;
import model.ingame.physics.MovementHandler;
import model.level.MapModel;
import util.Coordinates;

public class StandardBehavior {

    public static void circleAround(IMobileEntity entity, IEntity target, MapModel map) {
        Coordinates pos = entity.getPos();
        MovementHandler  movementHandler = entity.getMovementHandler();
        if (map.canEnterAround(entity, pos)) {
            movementHandler.setDirectionVector(new Coordinates(0, 0));
            return;
        }
        Coordinates playerPos = target.getPos();
        Coordinates direction = new Coordinates(playerPos.x() - pos.x(), playerPos.y() - pos.y());
        movementHandler.setDirectionVector(direction.rotate(Math.PI / 2).normalize());
    }
}
