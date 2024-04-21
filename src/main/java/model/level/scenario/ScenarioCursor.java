package model.level.scenario;

import model.ingame.entity.IEnemy.EnemyFactory;
import model.ingame.entity.IEntity.EntityFactory;
import model.ingame.weapon.WeaponFactory;
import util.IUpdateable;
import util.TimeIntervalMappingsCursor;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ScenarioCursor implements IUpdateable {
    private static final double TICK_LENGTH = 0.1; // Try to spawn every 0.1 seconds
    private final TimeIntervalMappingsCursor<GameContext> cursor;
    private final WeaponGenerator weaponGenerator = new WeaponGenerator(TICK_LENGTH);
    private final EnemyGenerator enemyGenerator = new EnemyGenerator(TICK_LENGTH);
    private final MiscEntityGenerator miscEntityGenerator = new MiscEntityGenerator(TICK_LENGTH);

    private final Queue<WeaponFactory> weapons = new LinkedList<>();
    private final Queue<EnemyFactory> enemies = new LinkedList<>();
    private final Queue<EntityFactory> miscEntities = new LinkedList<>();

    private GameContext currentContext;

    public ScenarioCursor(Scenario scenario) {
        cursor = new TimeIntervalMappingsCursor<>(scenario);
        this.currentContext = cursor.getCurrentValue();
    }

    @Override
    public void update(double delta) {
        cursor.advanceTime(delta);
        if (currentContext != cursor.getCurrentValue()) {
            currentContext = cursor.getCurrentValue();
            if (currentContext instanceof GameContext.FixedSpawnRateContext fixedSpawnRateContext) {
                weaponGenerator.setElementRates(fixedSpawnRateContext.weaponRates());
                enemyGenerator.setElementRates(fixedSpawnRateContext.enemyRates());
                miscEntityGenerator.setElementRates(fixedSpawnRateContext.miscEntityRates());
            }
            else if (currentContext instanceof GameContext.OneShotSpawnContext oneShotSpawnContext) {
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
        if (currentContext instanceof GameContext.FixedSpawnRateContext) {
            weapons.addAll(weaponGenerator.nextElements());
            enemies.addAll(enemyGenerator.nextElements());
            miscEntities.addAll(miscEntityGenerator.nextElements());
        }
    }

    public WeaponFactory nextWeapon() {
        return weapons.poll();
    }

    public EnemyFactory nextEnemy() {
        return enemies.poll();
    }

    public EntityFactory nextMiscEntity() {
        return miscEntities.poll();
    }
}
