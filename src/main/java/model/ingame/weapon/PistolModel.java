package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.IEntity;

public class PistolModel extends ProjectileWeaponModel{

    public PistolModel(IEntity owner, GameModel gameModel) {
        super("Pistol", gameModel, owner, 1000);
    }

    @Override
    public IProjectile createProjectile() {
        return new BulletsModel(new Coordinates(owner.getPos()), gameModel);
    }

}
