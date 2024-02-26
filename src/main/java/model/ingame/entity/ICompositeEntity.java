package model.ingame.entity;

import java.util.List;

public interface ICompositeEntity extends IEntity{

    public List<? extends IEntity> getChildren();

}
