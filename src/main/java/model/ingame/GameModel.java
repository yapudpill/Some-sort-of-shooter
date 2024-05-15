package model.ingame;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Predicate;

import model.ingame.entity.CombatEntityModel;
import model.ingame.entity.EntityConstructor;
import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.RandomPositionSpawner;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.entity.WeaponEntity;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.PhysicsEngineModel;
import model.level.MapModel;
import model.level.scenario.Scenario;
import model.level.scenario.ScenarioCursor;
import util.Coordinates;
import util.IUpdateable;
import util.Pair;

public class GameModel implements IUpdateable {
    public final Statistics stats;
    private final PhysicsEngineModel physicsEngine;
    private final MapModel map;
    private final ScenarioCursor scenarioCursor;
    private final PlayerModel player;
    private boolean isRunning = true;

    private final Set<IEntity> entityModelList = new CopyOnWriteArraySet<>();
    private final Set<ICollisionEntity> collisionEntities = new CopyOnWriteArraySet<>();
    private final Set<IUpdateable> updateables = new CopyOnWriteArraySet<>();

    public GameModel(MapModel map, Statistics stats, Scenario scenario) {
        this.map = map;
        this.stats = stats;

        scenarioCursor = new ScenarioCursor(scenario);
        updateables.add(scenarioCursor);

        physicsEngine = new PhysicsEngineModel(map, collisionEntities);
        updateables.add(physicsEngine);

        player = new PlayerModel(map.getPlayerSpawn(), this);
        addEntity(player);

        for (Pair<Coordinates, EntityConstructor> pair : map.getInitialEntities()) {
            addEntity(pair.second().makeEntity(pair.first(), this));
        }

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
    }

    @Override
    public void update(double delta) {
        stats.survivedTime += delta;
        for (IUpdateable updateable : updateables) {
            updateable.update(delta);
        }

        if (scenarioCursor.isGameFinished()) isRunning = false;
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