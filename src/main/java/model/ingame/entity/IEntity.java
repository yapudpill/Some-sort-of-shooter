package model.ingame.entity;

import model.ingame.Coordinates;

public interface IEntity {

    Coordinates getPos();
    void setPos(Coordinates pos);
    double getWidth();
    double getHeight();

}
