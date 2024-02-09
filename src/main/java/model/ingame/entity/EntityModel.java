package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.weapon.WeaponModel;

public abstract class EntityModel extends MovingComponentModel {
    protected WeaponModel weapon;

    public EntityModel(Coordinates pos) {
        super(pos);
        //TODO Auto-generated constructor stub
    }

    public void startShooting(){
        // weapon.holdTrigger();
    }

    public void stopShooting(){
        // weapon.releaseTrigger();
    }

    public void setWeapon(WeaponModel weapon) {
        this.weapon = weapon;
    }
    
}
