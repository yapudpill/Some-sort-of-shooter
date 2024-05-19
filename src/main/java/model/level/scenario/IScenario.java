package model.level.scenario;

import model.ingame.GameModel;

/**
 * A scenario. Contains the information about the entities and weapons that should be spawned at a specific time.
 */
public interface IScenario {
    IScenarioCursor createCursor(GameModel gameModel);
}
