package model.ingame.entity;

public interface EntityMaker<T extends IEntity> {

    T makeEntity(double x, double y);

}
