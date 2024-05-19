package model.level.scenario;

import model.ingame.entity.EntityConstructor;
import model.ingame.weapon.WeaponConstructor;

import java.util.Collection;
import java.util.Map;

/**
 * A game context. Contains the information about the entities and weapons that should be spawned at a specific time.
 * <p>
 * Can be either a one-shot spawn context or a fixed spawn rate context. The one-shot spawn context should spawn all
 * the entities and weapons at once, while the fixed spawn rate context should spawn entities and weapons with a fixed
 * probability.
 */
public interface IGameContext {
    record OneShotSpawnContext(
        Collection<WeaponConstructor> weapons,
        Collection<EntityConstructor> enemies,
        Collection<EntityConstructor> miscEntities
    )implements IGameContext {}

    record FixedSpawnRateContext(
        Map<WeaponConstructor, Double> weaponRates,
        Map<EntityConstructor, Double> enemyRates,
        Map<EntityConstructor, Double> miscEntityRates
    ) implements IGameContext {}
}