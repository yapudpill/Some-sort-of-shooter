package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.IUpdateable;

public abstract class EntityModel implements IEntity{
    protected GameModel gameModel;
    protected Coordinates pos;
    protected double width;
    protected double height;

    public EntityModel(Coordinates pos, double width, double height, GameModel gameModel) {
        if (pos == null)
            throw new IllegalArgumentException("Position cannot be null");
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.gameModel = gameModel;
        gameModel.addEntity(this);
    }

    public void despawn() {
        gameModel.removeEntity(this);
        if (this instanceof IUpdateable iUpdateable) gameModel.detachAsUpdateable(iUpdateable);
    }

    public Coordinates getPos() {
        return pos;
    }

    public void setPos(Coordinates pos) {
        this.pos = pos;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
