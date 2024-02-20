package model.ingame.entity;

import model.ingame.Coordinates;

public abstract class EntityModel implements IEntity{
    protected Coordinates pos;
    protected double width;
    protected double height;

    public EntityModel(Coordinates pos, double width, double height) {
        if (pos == null)
            throw new IllegalArgumentException("Position cannot be null");
        this.pos = pos;
        this.width = width;
        this.height = height;
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
