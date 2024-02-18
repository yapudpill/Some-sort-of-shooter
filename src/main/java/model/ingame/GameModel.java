package model.ingame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.ingame.entity.EntityModel;
import model.ingame.entity.PlayerModel;
import model.ingame.physics.PhysicsEngineModel;
import model.level.MapModel;

public class GameModel implements IUpdateable {
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
	private final PlayerModel player;

    private final Set<EntityModel> entityModelList = new HashSet<>();

		
	public GameModel(String path){
        this(new MapModel(path));
    }

    public GameModel(MapModel map) {
        this.map = map;
        this.physicsEngine = new PhysicsEngineModel(map);
        this.player = new PlayerModel(physicsEngine);
        entityModelList.add(player);
    }

    @Override
    public void update() {
	    player.update();
    }

    public MapModel getMapModel() {
        return map;
    }

    public Set<EntityModel> getEntitySet() {
        return entityModelList;
    }

    // Probably temporary
    public PlayerModel getPlayer() {
        return player;
    }
}