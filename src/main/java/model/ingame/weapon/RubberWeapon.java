package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.IEntity;

public class RubberWeapon extends ProjectileWeaponModel {

    public RubberWeapon(IEntity owner, GameModel gameModel) {
        super("Rubber Gun", "rubbergun", gameModel, owner, 1*60);
        //TODO Auto-generated constructor stub
    }

    @Override
    public IProjectile createProjectile() {
        return new RubberProjectile(owner.getPos(), this, gameModel);
    }

}
