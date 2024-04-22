package model.level.scenario;

import model.ingame.weapon.WeaponModel;

import java.util.Map;
import java.util.Set;

import static model.ingame.entity.IEnemy.IEnemyFactory;
import static model.ingame.entity.IEntity.IEntityFactory;


public interface GameContext {
    record OneShotSpawnContext(Set<WeaponModel.IWeaponFactory> weapons,
                        Set<IEnemyFactory> enemies,
                        Set<IEntityFactory> miscEntities) implements GameContext {
    }

    record FixedSpawnRateContext(Map<Double, WeaponModel.IWeaponFactory> weaponRates,
                                 Map<Double, IEnemyFactory> enemyRates,
                                 Map<Double, IEntityFactory> miscEntityRates) implements GameContext {
    }
}