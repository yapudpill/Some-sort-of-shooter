package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;

public class GravityGun extends ProjectileWeaponModel {

    public GravityGun(ICombatEntity owner, GameModel gameModel) {
        super("gravitygun", "gravitygun", owner, gameModel, 5);

    }

    @Override
    public Projectile createProjectile() {
        return new BlackHoleProjectile(owner.getPos(), this, gameModel);
    }

    @Override
    public void fire() {
        Projectile projectile = createProjectile();
        projectile.setPos(owner.getPos());
        projectile.getMovementHandler().setDirectionVector(this.directionVector);
        gameModel.attachAsUpdateable(projectile);
        gameModel.addEntity(projectile);
        ((BlackHoleProjectile) projectile).startCollapseTimer();
    }
}
