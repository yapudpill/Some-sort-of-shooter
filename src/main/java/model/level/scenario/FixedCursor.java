package model.level.scenario;

import model.ingame.entity.EntityConstructor;
import model.ingame.weapon.WeaponConstructor;
import model.level.scenario.IGameContext.FixedSpawnRateContext;
import model.level.scenario.IGameContext.OneShotSpawnContext;
import util.IntervalMapCursor;
import util.WeightedRandomGenerator;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


/**
 * Cursor for a fixed scenario. Allows to iterate over the scenario's elements and provides the next elements to spawn.
 */
public class FixedCursor extends IntervalMapCursor<Double, IGameContext> implements IScenarioCursor {
    private final WeightedRandomGenerator<WeaponConstructor> weaponGenerator;
    private final WeightedRandomGenerator<EntityConstructor> enemyGenerator;
    private final WeightedRandomGenerator<EntityConstructor> miscEntityGenerator;

    private final Queue<WeaponConstructor> weapons = new LinkedList<>();
    private final Queue<EntityConstructor> enemies = new LinkedList<>();
    private final Queue<EntityConstructor> miscEntities = new LinkedList<>();

    private IGameContext currentContext;

    public FixedCursor(FixedScenario scenario) {
        super(scenario, Double::sum);

        weaponGenerator = new WeightedRandomGenerator<>();
        enemyGenerator = new WeightedRandomGenerator<>();
        miscEntityGenerator = new WeightedRandomGenerator<>();
    }

    @Override
    public void update(double delta) {
        advance(delta);
        if (hasChanged() || hasLooped() || currentContext == null) {
            currentContext = getCurrentValue();
            if (currentContext instanceof FixedSpawnRateContext context) {
                weaponGenerator.setElementRates(context.weaponRates());
                enemyGenerator.setElementRates(context.enemyRates());
                miscEntityGenerator.setElementRates(context.miscEntityRates());
            } else if (currentContext instanceof OneShotSpawnContext context) {
                // Disable the random generators
                weaponGenerator.setElementRates(Map.of());
                enemyGenerator.setElementRates(Map.of());
                miscEntityGenerator.setElementRates(Map.of());

                // Add all the one-shot spawns to the spawn queue
                weapons.addAll(context.weapons());
                enemies.addAll(context.enemies());
                miscEntities.addAll(context.miscEntities());
            }
        }

        // If spawning at a fixed rate, add the next elements to the spawn queue
        if (currentContext instanceof IGameContext.FixedSpawnRateContext) {
            weapons.addAll(weaponGenerator.nextElements(delta));
            enemies.addAll(enemyGenerator.nextElements(delta));
            miscEntities.addAll(miscEntityGenerator.nextElements(delta));
        }
    }

    @Override
    public WeaponConstructor nextWeapon() {
        return weapons.poll();
    }

    @Override
    public EntityConstructor nextEnemy() {
        return enemies.poll();
    }

    @Override
    public EntityConstructor nextMiscEntity() {
        return miscEntities.poll();
    }

    @Override
    public boolean isGameFinished() {
        return hasEnded();
    }
}
