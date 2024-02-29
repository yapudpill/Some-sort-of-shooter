package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;

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
