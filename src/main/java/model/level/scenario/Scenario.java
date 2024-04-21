package model.level.scenario;

import util.EndReachedBehaviour;
import util.IntervalMap;

public class Scenario extends IntervalMap<Double, GameContext> {
    public Scenario(EndReachedBehaviour endReachedBehaviour) {
        super(0., Double::sum, endReachedBehaviour);
    }
}
