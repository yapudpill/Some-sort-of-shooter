package model.level.scenario;

import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;

import java.util.Map;

import static model.ingame.entity.IEnemy.EnemyFactory;

public class EnemyParser {
    public static final Map<String, EnemyFactory> AVAILABLE_ENEMIES = Map.of(
            "ExplodingEnemy", ExplodingEnemy::new,
            "SmartEnemy", SmartEnemyModel::new,
            "WalkingEnemy", WalkingEnemyModel::new
    );

    public static EnemyFactory getEnemyFactory(String enemyName) {
        return AVAILABLE_ENEMIES.get(enemyName);
    }
}