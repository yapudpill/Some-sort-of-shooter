package model.ingame;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import model.ingame.entity.EnemySpawnerModel;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.RandomSpawnerModel;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.SmartEnemySpawner;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.PhysicsEngineModel;
import model.ingame.weapon.RandomWeaponSpawner;
import model.level.InvalidMapException;
import model.level.MapModel;
import util.IUpdateable;
import util.Resource;

public class GameModel implements IUpdateable {
    public final Statistics stats;
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
    private final PlayerModel player;
    private boolean isRunning = true;

    private final Set<IEntity> entityModelList = new CopyOnWriteArraySet<>();
    private final Set<IUpdateable> updateables = new CopyOnWriteArraySet<>();

    public GameModel(Resource mapResource) throws InvalidMapException {
        stats = new Statistics(mapResource);
        map = new MapModel(mapResource);
        physicsEngine = new PhysicsEngineModel(map);
        player = new PlayerModel(map.getPlayerSpawn(), this);

        entityModelList.add(player);
        updateables.add(player);
        updateables.add(new RandomWeaponSpawner(this));
        initSpawner();
        FloodFillPathFinder floodFillPathFinder = new FloodFillPathFinder(this, 0.1);
        WalkingEnemyModel.setPathFinder(floodFillPathFinder);
        SmartEnemyModel.setPathFinder(floodFillPathFinder);
    }

    @Override
    public void update(double delta) {
        stats.survivedTime += delta;
        for (IUpdateable updateable : updateables) {
            updateable.update(delta);
        }
        if(player.isDead()){
            isRunning = false;
        }
    }

    public void initSpawner() {
        RandomSpawnerModel mainSpawner = new RandomSpawnerModel(
            this,
            List.of(new EnemySpawnerModel(this), new SmartEnemySpawner(this)),
            3
        );
        mainSpawner.start();
    }

    public MapModel getMapModel() {
        return map;
    }

    public Set<IEntity> getEntitySet() {
        return entityModelList;
    }

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

    public boolean isRunning() {
        return isRunning;
    }
}