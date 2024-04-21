package model.ingame;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

import model.ingame.entity.BreakableBarrier;
import model.ingame.entity.CombatEntityModel;
import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.RandomEnemySpawner;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.PhysicsEngineModel;
import model.ingame.weapon.RandomWeaponSpawner;
import model.level.InvalidMapException;
import model.level.MapModel;
import util.Coordinates;
import util.IUpdateable;
import util.Resource;

public class GameModel implements IUpdateable {
    public final Statistics stats;
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
    private final PlayerModel player;
    private boolean isRunning = true;

    private final Set<IEntity> entityModelList = new CopyOnWriteArraySet<>();
    private final Set<ICollisionEntity> collisionEntities = new CopyOnWriteArraySet<>();
    private final Set<IUpdateable> updateables = new CopyOnWriteArraySet<>();

    public GameModel(Resource mapResource) throws InvalidMapException {
        stats = new Statistics(mapResource);
        map = new MapModel(mapResource);

        physicsEngine = new PhysicsEngineModel(map, collisionEntities);
        updateables.add(physicsEngine);

        player = new PlayerModel(map.getPlayerSpawn(), this);
        addEntity(player);

        updateables.add(new RandomWeaponSpawner(this, 5));
        updateables.add(new RandomEnemySpawner(this, 4));

        ExplodingEnemy enemyFinderInstance = new ExplodingEnemy(Coordinates.ZERO, this);
        enemyFinderInstance.despawn();
        FloodFillPathFinder floodFillPathFinder = new FloodFillPathFinder(this, 0.1, enemyFinderInstance);
        Predicate<Coordinates> avoidPredicate = pos -> map.getTile(pos).getCollidablesSet()
        .stream().anyMatch(entity -> !(entity instanceof PlayerModel) && entity instanceof CombatEntityModel);
        floodFillPathFinder.setAvoidPredicate(avoidPredicate);
        WalkingEnemyModel.setPathFinder(floodFillPathFinder);
        SmartEnemyModel.setPathFinder(floodFillPathFinder);
        ExplodingEnemy.setPathFinder(floodFillPathFinder);

        // TODO: read this from map
        // add breakable barriers
        BreakableBarrier barrier1 = new BreakableBarrier(new Coordinates(5.5, 5.5), this);
        BreakableBarrier barrier2 = new BreakableBarrier(new Coordinates(5.5, 6.5), this);
        BreakableBarrier barrier3 = new BreakableBarrier(new Coordinates(5.5, 7.5), this);
        entityModelList.add(barrier1);
        entityModelList.add(barrier2);
        entityModelList.add(barrier3);
    }

    @Override
    public void update(double delta) {
        stats.survivedTime += delta;
        for (IUpdateable updateable : updateables) {
            updateable.update(delta);
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
        if (entity instanceof ICollisionEntity col) {
            collisionEntities.add(col);
        }
        if (entity instanceof IUpdateable updateable) {
            updateables.add(updateable);
        }
    }

    public void removeEntity(IEntity entity) {
        entityModelList.remove(entity);
    }

    public void removeCollisionEntity(ICollisionEntity entity) {
        collisionEntities.remove(entity);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}