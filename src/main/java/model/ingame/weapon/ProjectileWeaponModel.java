package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.IEntity;

public abstract class ProjectileWeaponModel extends WeaponModel {
    public ProjectileWeaponModel(String name, String identifier, GameModel gameModel, IEntity owner, int coolDown) {
        super(name, identifier, gameModel, owner, coolDown);
    }

    public abstract IProjectile createProjectile();

    public boolean attack() {
        if (isCoolingDown()) {
            System.out.println("Weapon is cooling down. Cannot shoot.");
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
