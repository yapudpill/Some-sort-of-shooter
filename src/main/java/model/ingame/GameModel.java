package model.ingame;

import java.util.List;

import model.ingame.entity.IEntity;
import model.ingame.entity.IMobileEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.physics.IMovementHandler;
import model.ingame.physics.PhysicsEngineModel;
import model.level.MapModel;

public class GameModel implements IUpdateable {
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
	private final PlayerModel player;
    private final List<IEntity> entities;
		
	public GameModel(String path){
		this.map = new MapModel(path);
		this.physicsEngine = new PhysicsEngineModel(map);
		this.player = new PlayerModel(physicsEngine);
        this.entities = List.of(player);
    }

    @Override
    public void update() {
		    player.update();
    }
}