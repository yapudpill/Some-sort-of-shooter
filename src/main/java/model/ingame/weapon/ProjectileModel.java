package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.entity.CollisionEntityModel;
import model.ingame.entity.IMobileEntity;
import model.ingame.physics.IMovementHandler;

public abstract class ProjectileModel extends CollisionEntityModel implements IMobileEntity{
    protected int damage;

    public ProjectileModel(Coordinates pos, double width, double height, int damage) {
        super(pos, width, height);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
    
    public abstract void specialEffect();
}
