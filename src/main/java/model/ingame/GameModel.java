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
import model.ingame.weapon.WeaponSpawner;
import model.level.MapModel;

public class GameModel implements IUpdateable {
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
    private final PlayerModel player;
    private boolean isRunning = true;


    private final Set<IEntity> entityModelList = new CopyOnWriteArraySet<>();
    private final Set<IUpdateable> updateables = new CopyOnWriteArraySet<>();


    public GameModel(String path) {
        this(new MapModel(path));
    }

    public GameModel(MapModel map) {
        this.map = map;
        this.physicsEngine = new PhysicsEngineModel(map);
        this.player = new PlayerModel(this);
        entityModelList.add(player);
        updateables.add(player);
        RandomSpawnerModel mainSpawner = new RandomSpawnerModel(this, List.of( new WeaponSpawner(this), new SmartEnemySpawner(this)), 1*60);
        mainSpawner.start();
        WalkingEnemyModel.setPathFinder(new FloodFillPathFinder(this, 7));
        SmartEnemyModel.setPathFinder(new FloodFillPathFinder(this, 7));
    }

    @Override
    public void update() {
        for (IUpdateable updateable : updateables) updateable.update();
        if(player.isDead()){
            isRunning = false;
        }
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

    public boolean isAttachedAsUpdateable(IUpdateable updateable) {
        return updateables.contains(updateable);
    }

    public void reset() {
        entityModelList.clear();
        updateables.clear();
        isRunning = true;
        player.reset();
        entityModelList.add(player);
        updateables.add(player);
        RandomSpawnerModel mainSpawner = new RandomSpawnerModel(this, List.of( new WeaponSpawner(this), new SmartEnemySpawner(this)), 1*60);
        mainSpawner.start();
    }
}