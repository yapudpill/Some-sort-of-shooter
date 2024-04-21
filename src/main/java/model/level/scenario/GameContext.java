package model.level.scenario;

import model.ingame.weapon.WeaponFactory;

import java.util.Map;
import java.util.Set;

import static model.ingame.entity.IEnemy.EnemyFactory;
import static model.ingame.entity.IEntity.EntityFactory;


public interface GameContext {
    record OneShotSpawnContext(Set<WeaponFactory> weapons,
                        Set<EnemyFactory> enemies,
                        Set<EntityFactory> miscEntities) implements GameContext {
    }

    record FixedSpawnRateContext(Map<Double, WeaponFactory> weaponRates,
                                 Map<Double, EnemyFactory> enemyRates,
                                 Map<Double, EntityFactory> miscEntityRates) implements GameContext {
    }
}