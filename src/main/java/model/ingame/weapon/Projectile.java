package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.CollisionEntityModel;
import model.ingame.entity.IEffectEntity;
import model.ingame.entity.IMobileEntity;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.physics.MovementHandler;
import util.Coordinates;

/**
 * Abstract class for all projectiles in the game. Keeps a reference to the weapon that fired it and the damage it deals.
 */
public abstract class Projectile extends CollisionEntityModel implements IMobileEntity, IEffectEntity {
    protected final ProjectileWeaponModel sourceWeapon;
    protected final double damage;
    protected final MovementHandler movementHandler;
    protected boolean active;

    public Projectile(Coordinates pos, ProjectileWeaponModel source, double width, double height, double damage, GameModel gameModel) {
        super(pos, width, height, gameModel);
        this.sourceWeapon = source;
        this.damage = damage*sourceWeapon.owner.getDamageMultiplier();
        this.movementHandler = new MovementHandler(this, gameModel.getPhysicsEngine());
        this.active = true;
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        boolean condition = true;
        if (sourceWeapon.getOwner() instanceof IEffectEntity effectEntity) {
            condition = effectEntity.canApplyEffect(target);
        }
        if (target != sourceWeapon.getOwner() && condition) {
            return true;
        }
        return false;
    }

    @Override
    public MovementHandler getMovementHandler() {
        return movementHandler;
    }

    @Override
    public void update(double delta) {
        movementHandler.update(delta);
    }
}
