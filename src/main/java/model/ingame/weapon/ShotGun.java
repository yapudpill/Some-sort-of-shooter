package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;
import model.ingame.physics.DamageListener;

public class ShotGun extends ProjectileWeaponModel {

    public ShotGun(ICombatEntity owner, GameModel gameModel) {
        super("Shotgun", "shotgun", gameModel, owner, 60);
    }

    @Override
    public IProjectile createProjectile() {
        return new BulletsModel(owner.getPos(), this, gameModel) {{
            addCollisionListener(new DamageListener(damage, e -> despawn()));
        }};
    }

    @Override
    public boolean attack() {
        if(isCoolingDown()){
            return false;
        }
        for (int i = 0; i < 5; i++) {
            setDirectionVector(getDirectionVector().rotate(Math.random() * 0.5 - 0.25));
            fire();
        }
        return true;
    }
}
