package model.ingame.entity;

import model.ingame.Coordinates;

public abstract class EnemyModel extends MovingComponentModel{
    
    public EnemyModel(Coordinates pos) {
        super(pos);
    }
    
}
