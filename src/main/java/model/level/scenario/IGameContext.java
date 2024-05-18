package model.level.scenario;

import model.ingame.entity.EntityConstructor;
import model.ingame.weapon.WeaponConstructor;

import java.util.Collection;
import java.util.Map;

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