package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.IEntity;
import model.ingame.physics.DamageListener;

public class PistolModel extends ProjectileWeaponModel{

    public PistolModel(IEntity owner, GameModel gameModel) {
        super("Pistol", gameModel, owner, 60);
    }

    @Override
    public IProjectile createProjectile() {
        return new BulletsModel(owner.getPos(), this, gameModel) {{
            addCollisionListener(new DamageListener(damage));
        }};
    }
}
