package model.ingame;

import model.ingame.entity.*;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.PhysicsEngineModel;
import model.level.InvalidMapException;
import model.level.MapModel;
import model.level.scenario.Scenario;
import model.level.scenario.ScenarioCursor;
import util.Coordinates;
import util.IUpdateable;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

public class GameModel implements IUpdateable {
    public final Statistics stats;
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
    private final Scenario scenario;
    private final ScenarioCursor scenarioCursor;
    private final PlayerModel player;
    private boolean isRunning = true;

    private final Set<IEntity> entityModelList = new CopyOnWriteArraySet<>();
    private final Set<ICollisionEntity> collisionEntities = new CopyOnWriteArraySet<>();
    private final Set<IUpdateable> updateables = new CopyOnWriteArraySet<>();

    public GameModel(MapModel map, Statistics stats, Scenario scenario) throws InvalidMapException {
        this.stats = stats;
        this.map = map;
        this.scenario = scenario;
        this.scenarioCursor = new ScenarioCursor(scenario);
        this.updateables.add(scenarioCursor);

        physicsEngine = new PhysicsEngineModel(map, collisionEntities);
        player = new PlayerModel(map.getPlayerSpawn(), this);
        this.addEntity(player);
        updateables.add(physicsEngine);

        updateables.add(new RandomPositionSpawner(this, scenarioCursor::nextEnemyFactory));
        updateables.add(new RandomPositionSpawner(this, () -> WeaponEntity.weaponEntityFactory(scenarioCursor.nextWeaponFactory())));
        updateables.add(new RandomPositionSpawner(this, scenarioCursor::nextMiscEntityFactory));

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