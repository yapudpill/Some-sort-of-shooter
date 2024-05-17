package model.level.scenario;

import model.ingame.GameModel;

public interface IScenario {
    IScenarioCursor createCursor(GameModel gameModel);
}
