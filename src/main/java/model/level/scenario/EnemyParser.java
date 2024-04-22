package model.level.scenario;

import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;

import java.util.Map;

import static model.ingame.entity.IEnemy.IEnemyFactory;

public class EnemyParser {
    public static final Map<String, IEnemyFactory> AVAILABLE_ENEMIES = Map.of(
            "ExplodingEnemy", ExplodingEnemy::new,
            "SmartEnemy", SmartEnemyModel::new,
            "WalkingEnemy", WalkingEnemyModel::new
    );

    public static IEnemyFactory getEnemyFactory(String enemyName) {
        return AVAILABLE_ENEMIES.get(enemyName);
    }
}