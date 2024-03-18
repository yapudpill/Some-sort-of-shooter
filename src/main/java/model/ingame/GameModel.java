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
import model.level.MapModel;

public class GameModel implements IUpdateable {
    public final Statistics stats;
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
    private final PlayerModel player;
    private boolean isRunning = true;


    private final Set<IEntity> entityModelList = new CopyOnWriteArraySet<>();
    private final Set<IUpdateable> updateables = new CopyOnWriteArraySet<>();


    public GameModel(String mapName) {
        stats = new Statistics(mapName);
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
        stats.survivedFrames++;
        for (IUpdateable updateable : updateables) updateable.update();
        System.out.println(player.getMovementHandler().getDirectionVector());
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
}