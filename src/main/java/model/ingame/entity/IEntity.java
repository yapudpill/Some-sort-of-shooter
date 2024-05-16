package model.ingame.entity;

import util.Coordinates;

public interface IEntity {
    Coordinates getPos();
    void setPos(Coordinates pos);
    double getWidth();
    double getHeight();
}
