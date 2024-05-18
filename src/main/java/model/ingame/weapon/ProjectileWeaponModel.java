package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;

public abstract class ProjectileWeaponModel extends WeaponModel {
    public ProjectileWeaponModel(String name, String identifier, ICombatEntity owner, GameModel gameModel, double coolDown) {
        super(name, identifier, owner, gameModel, coolDown);
    }

    public abstract Projectile createProjectile();

    @Override
    public boolean attack() {
        if (isCoolingDown()) {
            return false;
        }
        fire();
        coolDownTimer.start();
        return true;
    }

    public void fire() {
        Projectile projectile = createProjectile();
        projectile.setPos(owner.getPos());
        projectile.getMovementHandler().setDirectionVector(this.directionVector);
        gameModel.attachAsUpdateable(projectile);
        gameModel.addEntity(projectile);
    }
}
