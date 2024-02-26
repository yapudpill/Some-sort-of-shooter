package model.ingame;

import model.ingame.weapon.RandomWeaponSpawner;
import model.ingame.entity.EnemySpawnerModel;
import model.ingame.entity.IEntity;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.physics.PhysicsEngineModel;
import model.level.MapModel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GameModel implements IUpdateable {
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
    private final PlayerModel player;

    private final Set<IEntity> entityModelList = new CopyOnWriteArraySet<>();
    private final Set<IUpdateable> updateables = new CopyOnWriteArraySet<>();


    public GameModel(String path) {
        this(new MapModel(path));
    }

    public GameModel(MapModel map) {
        this.map = map;
        this.physicsEngine = new PhysicsEngineModel(map);
        this.player = new PlayerModel(physicsEngine);
        entityModelList.add(player);
        updateables.add(player);
        updateables.add(new RandomWeaponSpawner(this));
        updateables.add(new EnemySpawnerModel(this));
    }

    @Override
    public void update() {
        // remove vulnerable entities if dead
        updateables.removeIf(updateable -> updateable instanceof IVulnerableEntity v && v.isDead());
        for (IUpdateable updateable : updateables) {
            updateable.update();
        }
        System.out.println("player health : " + player.getHealth());
        System.out.println("player weapon: " + player.getWeapon());
    }

    public MapModel getMapModel() {
        return map;
    }

    public Set<IEntity> getEntitySet() {
        return entityModelList;
    }

    // Probably temporary
    public PlayerModel getPlayer() {
        return player;
    }

    public Collection<IUpdateable> getUpdateables() {
        return updateables;
    }

    public PhysicsEngineModel getPhysicsEngine() {
        return physicsEngine;
    }
}