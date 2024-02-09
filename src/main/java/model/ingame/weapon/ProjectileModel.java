package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.MapComponentModel;
import model.ingame.entity.MovingComponentModel;

public abstract class ProjectileModel extends MovingComponentModel {
    private int damage;

    public ProjectileModel(Coordinates pos) {
        super(pos);
        //TODO: implement projectile
    }

    public void update() {
        this.move();
    }
}
