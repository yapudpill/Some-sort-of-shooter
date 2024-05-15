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

    record FixedSpawnRateContext(Map<WeaponModel.IWeaponFactory, Double> weaponRates,
                                 Map<IEnemyFactory, Double> enemyRates,
                                 Map<IEntityFactory, Double> miscEntityRates) implements IGameContext { }
}