package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;
import util.Coordinates;

public abstract class ProjectileWeaponModel extends WeaponModel {
    public ProjectileWeaponModel(String name, String identifier, GameModel gameModel, ICombatEntity owner, double coolDown) {
        super(name, identifier, gameModel, owner, coolDown);
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
        projectile.setPos(new Coordinates(owner.getPos()));
        projectile.getMovementHandler().setDirectionVector(this.directionVector);
        gameModel.attachAsUpdateable(projectile);
        gameModel.addEntity(projectile);
    }
}
