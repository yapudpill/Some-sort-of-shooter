package model.ingame.entity;

import util.Coordinates;

/**
 *  Interface for entities that can be placed in the game.
 *  <p>
 *  Both the interface and the abstract class are necessary because java doesn't support multiple inheritance, and some
 *  components may be an IEntity without extending {@link EntityModel}
 */
public interface IEntity {
    Coordinates getPos();
    void setPos(Coordinates pos);
    double getWidth();
    double getHeight();
}
