package model.level.scenario;

import java.util.Collection;

import static model.ingame.entity.IEnemy.EnemyFactory;

public class EnemyGenerator extends WeightedRandomElementGenerator<EnemyFactory> {
    public EnemyGenerator(double tickRate) {
        super(tickRate);
    }

    @Override
    public Collection<EnemyFactory> getAllowedElements() {
        return EnemyParser.AVAILABLE_ENEMIES.values();
    }
}
