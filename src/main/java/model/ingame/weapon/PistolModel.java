package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;
import model.ingame.physics.DamageListener;

public class PistolModel extends ProjectileWeaponModel {

    public PistolModel(ICombatEntity owner, GameModel gameModel) {
        super("Pistol", "pistol",  owner, gameModel, 0.1);
    }

    @Override
    public Projectile createProjectile() {
        return new BulletsModel(owner.getPos(), this, gameModel) {{
            addCollisionListener(new DamageListener(damage, e -> despawn()));
        }};
    }
}
