package model.ingame.weapon;

import model.ingame.entity.IEntity;
import model.ingame.physics.DamageListener;
import model.ingame.physics.PhysicsEngineModel;

public class PistolModel extends ProjectileWeaponModel{

    public PistolModel(IEntity owner, PhysicsEngineModel physicsEngine) {
        super("Pistol", physicsEngine, owner, 1000);
    }

    @Override
    public IProjectile createProjectile() {
        return new BulletsModel(owner.getPos(), this, physicsEngine) {{
            addCollisionListener(new DamageListener(damage));
        }};
    }
}
