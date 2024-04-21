package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;

@FunctionalInterface
interface EntityConstructor {
    IEntity makeEntity(Coordinates pos, GameModel gameModel);
}