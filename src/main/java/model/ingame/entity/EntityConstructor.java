package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;

/**
 * Functional interface for entity constructors.
 */
@FunctionalInterface
public interface EntityConstructor {
    IEntity makeEntity(Coordinates pos, GameModel gameModel);
}