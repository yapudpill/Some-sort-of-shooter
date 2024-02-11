package model.ingame;

import java.util.List;

import model.ingame.entity.IMobileEntity;
import model.ingame.physics.IMovementHandler;
import model.ingame.physics.PhysicsEngineModel;
import model.level.MapModel;

public class GameModel implements IUpdateable {
    private PhysicsEngineModel physicsEngine;
    private MapModel map;
    private List<IMobileEntity> mobileEntities;
    private List<IMovementHandler> movementHandlers;

    @Override
    public void update() {
    
    }
}
