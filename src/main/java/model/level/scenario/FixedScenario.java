package model.level.scenario;

import model.ingame.GameModel;
import util.EndReachedBehaviour;
import util.IntervalMap;

/**
 * A fixed scenario. The scenario is defined by a set of fixed game contexts that are triggered at specific times.
 *
 * @see IGameContext
 * @see IntervalMap
 */
public class FixedScenario extends IntervalMap<Double, IGameContext> implements IScenario {
    public FixedScenario(EndReachedBehaviour endReachedBehaviour) {
        super(0., (x, y) -> x % y, endReachedBehaviour);
    }

    @Override
    public IScenarioCursor createCursor(GameModel gameModel) {
        return new FixedCursor(this);
    }
}
