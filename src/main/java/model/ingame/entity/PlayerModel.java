package model.ingame.entity;

import model.ingame.Coordinates;

/*
 * Abstract class for the player model
 */
public abstract class PlayerModel extends EntityModel{
    
    public PlayerModel(Coordinates pos) {
        super(pos);
    }

}
