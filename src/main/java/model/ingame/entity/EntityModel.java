package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;
import util.IUpdateable;

abstract class EntityModel implements IEntity {
    protected GameModel gameModel;
    protected Coordinates pos;
    protected double width, height;

    EntityModel(Coordinates pos, double width, double height, GameModel gameModel) {
        if (pos == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.gameModel = gameModel;
    }

    void despawn() {
        gameModel.removeEntity(this);
        if (this instanceof IUpdateable iUpdateable) {
            gameModel.detachAsUpdateable(iUpdateable);
        }
    }

    public Coordinates getPos() {
        return pos;
    }

    public void setPos(Coordinates pos) {
        this.pos = pos;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }
}
