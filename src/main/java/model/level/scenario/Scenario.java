package model.level.scenario;

import util.TimeIntervalMappings;

public class Scenario extends TimeIntervalMappings<GameContext> {
    public Scenario(EndReachedBehaviour endReachedBehaviour) {
        super(endReachedBehaviour);
    }
}
