package model.ingame;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import model.ingame.entity.EnemySpawnerModel;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.physics.PhysicsEngineModel;
import model.ingame.weapon.RandomWeaponSpawner;
import model.ingame.weapon.RocketLauncher;
import model.level.MapModel;

public class GameModel implements IUpdateable {
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
    private final PlayerModel player;

    private final Set<IEntity> entityModelList = new CopyOnWriteArraySet<>();
    private final Set<IUpdateable> updateables = new CopyOnWriteArraySet<>();


    public GameModel(String mapName) {
        map = new MapModel(mapName);
        physicsEngine = new PhysicsEngineModel(map);
        player = new PlayerModel(this);
        entityModelList.add(player);
        updateables.add(player);
        updateables.add(new RandomWeaponSpawner(this));
        updateables.add(new EnemySpawnerModel(this));
    }

    @Override
    public void update() {
        for (IUpdateable updateable : updateables){
            updateable.update();
        }
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

    public void attachAsUpdateable(IUpdateable updateable) {
        updateables.add(updateable);
    }

    public void detachAsUpdateable(IUpdateable updateable) {
        updateables.remove(updateable);
    }

    public void addEntity(IEntity entity) {
        entityModelList.add(entity);
    }

    public void removeEntity(IEntity entity) {
        Coordinates pos = entity.getPos();
        entityModelList.remove(entity);
        if(entity instanceof ICollisionEntity col) map.removeCollidableAt(col, (int)pos.x, (int)pos.y);
    }

}