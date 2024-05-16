package model.level.scenario;

import model.ingame.GameModel;
import util.EndReachedBehaviour;
import util.IntervalMap;

public class FixedScenario extends IntervalMap<Double, IGameContext> implements IScenario {
    public FixedScenario(EndReachedBehaviour endReachedBehaviour) {
        super(0., (x, y) -> x % y, endReachedBehaviour);
    }

    @Override
    public IScenarioCursor createCursor(GameModel gameModel) {
        return new FixedCursor(this);
    }
}
