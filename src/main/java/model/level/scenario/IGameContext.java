package model.level.scenario;

import model.ingame.weapon.WeaponModel;

import java.util.Collection;
import java.util.Map;

import static model.ingame.entity.IEnemy.IEnemyFactory;
import static model.ingame.entity.IEntity.IEntityFactory;


public interface IGameContext {
    record OneShotSpawnContext(Collection<WeaponModel.IWeaponFactory> weapons,
                               Collection<IEnemyFactory> enemies,
                               Collection<IEntityFactory> miscEntities) implements IGameContext {
    }

    record FixedSpawnRateContext(Map<Double, WeaponModel.IWeaponFactory> weaponRates,
                                 Map<Double, IEnemyFactory> enemyRates,
                                 Map<Double, IEntityFactory> miscEntityRates) implements IGameContext {
    }
}