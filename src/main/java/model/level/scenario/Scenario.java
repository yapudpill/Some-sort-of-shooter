package model.level.scenario;

import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.FirstAidKit;
import model.ingame.weapon.PistolModel;
import util.EndReachedBehaviour;
import util.IntervalMap;
import util.Resource;

import java.util.Set;

public class Scenario extends IntervalMap<Double, GameContext> {
    public Scenario(EndReachedBehaviour endReachedBehaviour) {
        super(0., (x, y) -> x % y, endReachedBehaviour);
    }

    public static Scenario loadScenario(Resource scenarioResource) {
        Scenario scenario = new Scenario(EndReachedBehaviour.LOOPING);
        //scenario.put(2., new GameContext.OneShotSpawnContext(Set.of(PistolModel::new), Set.of(WalkingEnemyModel::new), Set.of(FirstAidKit::new)));
        scenario.put(1., new GameContext.OneShotSpawnContext(Set.of(), Set.of(), Set.of(FirstAidKit::new)));
        scenario.put(2., new GameContext.OneShotSpawnContext(Set.of(PistolModel::new), Set.of(), Set.of()));
        scenario.put(3., new GameContext.OneShotSpawnContext(Set.of(), Set.of(ExplodingEnemy::new), Set.of()));
        return scenario;
    }
}
