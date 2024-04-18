package model.ingame.entity.behavior;

import model.ingame.Coordinates;
import model.ingame.entity.IEntity;
import model.ingame.entity.IMobileEntity;
import model.ingame.physics.IMovementHandler;
import model.level.MapModel;

public class StandardBehavior {

    public static void circleAround(IMobileEntity entity, IEntity target, MapModel map) {
        Coordinates pos = entity.getPos();
        IMovementHandler  movementHandler = entity.getMovementHandler();
        if(map.unwalkableAround((int)pos.x, (int)pos.y)){
            movementHandler.setDirectionVector(new Coordinates(0, 0));
            return;
        }
        Coordinates playerPos = target.getPos();
        Coordinates direction = new Coordinates(playerPos.x - pos.x, playerPos.y - pos.y);
        movementHandler.setDirectionVector(direction.rotate(Math.PI/2).normalize());
    }
}
