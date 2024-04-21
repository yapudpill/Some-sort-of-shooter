package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;

public interface IEntity {
    @FunctionalInterface
    public static interface EntityFactory {
        EntityModel make(Coordinates pos, GameModel gameModel);
    }
    Coordinates getPos();
    void setPos(Coordinates pos);
    double getWidth();
    double getHeight();
}
