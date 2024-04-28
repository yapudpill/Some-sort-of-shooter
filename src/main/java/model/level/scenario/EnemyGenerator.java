package model.level.scenario;

import java.util.Collection;

import static model.ingame.entity.IEnemy.IEnemyFactory;

public class EnemyGenerator extends WeightedRandomElementGenerator<IEnemyFactory> {
    @Override
    public Collection<IEnemyFactory> getAllowedElements() {
        return EnemyParser.AVAILABLE_ENEMIES.values();
    }
}
