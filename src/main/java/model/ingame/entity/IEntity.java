package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;

public interface IEntity {
    @FunctionalInterface
    interface IEntityFactory {
        IEntity make(Coordinates pos, GameModel gameModel);
    }
    Coordinates getPos();
    void setPos(Coordinates pos);
    double getWidth();
    double getHeight();
}
