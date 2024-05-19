package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.ICombatEntity;
import model.ingame.physics.DamageListener;

/**
 * Model for the gatling weapon. It fires a lot of bullets in a short amount of time, but overheats quickly.
 */
public class Gatling extends ProjectileWeaponModel implements ContinuousFireWeapon {
    private boolean isFiring;
    ModelTimer inBetweenTimer;
    int heat;
    int maxheat;
    ModelTimer coolingTimer;

    public Gatling(ICombatEntity owner, GameModel gameModel) {
        super("Gatling", "gatling", owner, gameModel, 0);
        this.isFiring = false;
        this.heat = 0;
        this.maxheat = 100;
        this.coolingTimer = new ModelTimer(0.016, true, this::cooling,gameModel);
        this.coolingTimer.start();
        this.inBetweenTimer = new ModelTimer(0.048, false, this::fire, gameModel);
    }

    @Override
    public Projectile createProjectile() {
        return new BulletsModel(owner.getPos(), this, gameModel) {{
            addCollisionListener(new DamageListener(damage, e -> despawn()));
        }};
    }

    @Override
    public boolean attack() {
        if (!isFiring){
            isFiring = true;
            fire();
        } else {
            isFiring = false;
            cooling();
        }
        return false;
    }

    @Override
    public void fire() {
        if (isFiring && heat < maxheat) {
            Projectile projectile = createProjectile();
            projectile.setPos(owner.getPos());
            projectile.getMovementHandler().setDirectionVector(getDirectionVector().rotate((Math.random() * 0.32 - 0.16)));
            gameModel.attachAsUpdateable(projectile);
            gameModel.addEntity(projectile);
            inBetweenTimer.start();
            heat++;
            gameModel.stats.nbAttacks++;
        }
    }
    public void cooling(){
        if (!isFiring && heat > 0){
            heat--;
        }
    }

    public int getHeat() {
        return heat;
    }

    public int getMaxheat() {
        return maxheat;
    }
}
