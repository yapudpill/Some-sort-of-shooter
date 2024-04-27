package model.level.scenario;

import model.ingame.entity.IEnemy.IEnemyFactory;
import model.ingame.entity.IEntity.IEntityFactory;
import model.ingame.weapon.WeaponModel.IWeaponFactory;
import util.IUpdateable;
import util.IntervalMapCursor;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class ScenarioCursor implements IUpdateable {
    private static final double TICK_LENGTH = 0.1; // Try to spawn every 0.1 seconds
    private final IntervalMapCursor<Double, IGameContext> cursor;
    private final WeaponGenerator weaponGenerator = new WeaponGenerator(TICK_LENGTH);
    private final EnemyGenerator enemyGenerator = new EnemyGenerator(TICK_LENGTH);
    private final MiscEntityGenerator miscEntityGenerator = new MiscEntityGenerator(TICK_LENGTH);

    private final Queue<IWeaponFactory> weapons = new LinkedList<>();
    private final Queue<IEnemyFactory> enemies = new LinkedList<>();
    private final Queue<IEntityFactory> miscEntities = new LinkedList<>();

    private IGameContext currentContext;

    public ScenarioCursor(Scenario scenario) {
        cursor = new IntervalMapCursor<>(scenario, Double::sum);
    }

    @Override
    public void update(double delta) {
        cursor.advance(delta);
        if (cursor.hasChanged() || cursor.hasLooped() || currentContext == null) {
            currentContext = cursor.getCurrentValue();
            if (currentContext instanceof IGameContext.FixedSpawnRateContext fixedSpawnRateContext) {
                weaponGenerator.setElementRates(fixedSpawnRateContext.weaponRates());
                enemyGenerator.setElementRates(fixedSpawnRateContext.enemyRates());
                miscEntityGenerator.setElementRates(fixedSpawnRateContext.miscEntityRates());
            } else if (currentContext instanceof IGameContext.OneShotSpawnContext oneShotSpawnContext) {
                // Disable the random generators
                weaponGenerator.setElementRates(Map.of());
                enemyGenerator.setElementRates(Map.of());
                miscEntityGenerator.setElementRates(Map.of());

                // Add all the one-shot spawns to the spawn queue
                weapons.addAll(oneShotSpawnContext.weapons());
                enemies.addAll(oneShotSpawnContext.enemies());
                miscEntities.addAll(oneShotSpawnContext.miscEntities());
            }
        }

        // If spawning at a fixed rate, add the next elements to the spawn queue
        if (currentContext instanceof IGameContext.FixedSpawnRateContext) {
            weapons.addAll(weaponGenerator.nextElements(delta));
            enemies.addAll(enemyGenerator.nextElements(delta));
            miscEntities.addAll(miscEntityGenerator.nextElements(delta));
        }
    }

    public IWeaponFactory nextWeaponFactory() {
        return weapons.poll();
    }

    public IEnemyFactory nextEnemyFactory() {
        return enemies.poll();
    }

    public IEntityFactory nextMiscEntityFactory() {
        return miscEntities.poll();
    }

    public boolean isGameFinished() {
        return cursor.hasEnded();
    }
}
