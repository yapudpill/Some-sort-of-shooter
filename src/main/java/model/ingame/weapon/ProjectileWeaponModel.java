package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;

public abstract class ProjectileWeaponModel extends WeaponModel {
    public ProjectileWeaponModel(String name, String identifier, GameModel gameModel, ICombatEntity owner, double coolDown) {
        super(name, identifier, gameModel, owner, coolDown);
    }

    public abstract IProjectile createProjectile();

    public boolean attack() {
        if (isCoolingDown()) {
            return false;
        }

        IProjectile projectile = createProjectile();
        projectile.setPos(new Coordinates(owner.getPos()));
        projectile.getMovementHandler().setDirectionVector(this.directionVector);
        gameModel.attachAsUpdateable(projectile);
        gameModel.addEntity(projectile);
        coolDownTimer.start();
        return true;
    }
}
