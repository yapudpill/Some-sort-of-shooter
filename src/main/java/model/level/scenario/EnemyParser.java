package model.level.scenario;

import model.ingame.entity.EntityConstructor;
import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;

import java.util.Map;


public class EnemyParser {
    public static final Map<String, EntityConstructor> AVAILABLE_ENEMIES = Map.of(
            "ExplodingEnemy", ExplodingEnemy::new,
            "SmartEnemy", SmartEnemyModel::new,
            "WalkingEnemy", WalkingEnemyModel::new
    );

    public static EntityConstructor getEnemyFactory(String enemyName) {
        return AVAILABLE_ENEMIES.get(enemyName);
    }
}