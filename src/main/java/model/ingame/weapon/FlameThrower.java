package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.ICombatEntity;

public class FlameThrower extends ProjectileWeaponModel implements ContinuousFireWeapon {
    private boolean isFiring;
    ModelTimer inBetweenTimer;
    int heat;
    int maxheat;
    ModelTimer coolingTimer;

    public FlameThrower(ICombatEntity owner, GameModel gameModel) {
        super("Flamethrower", "flamethrower", owner, gameModel, 0);
        this.isFiring = false;
        this.heat = 0;
        this.maxheat = 300;
        this.coolingTimer = new ModelTimer(0.016, true, this::cooling,gameModel);
        this.coolingTimer.start();
        this.inBetweenTimer = new ModelTimer(0.016, false, this::fire, gameModel);
    }

    @Override
    public Projectile createProjectile() {
        return new FlameProjectileModel(getOwner().getPos(), this, gameModel);
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
        return true;
    }

    @Override
    public void fire() {
        if (isFiring && heat < maxheat) {
            Projectile projectile = createProjectile();
            projectile.setPos(owner.getPos());
            projectile.getMovementHandler().setDirectionVector(getDirectionVector().rotate((Math.random() * 0.50 - 0.25)));
            gameModel.attachAsUpdateable(projectile);
            gameModel.addEntity(projectile);
            inBetweenTimer.start();
            heat+=2;
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
