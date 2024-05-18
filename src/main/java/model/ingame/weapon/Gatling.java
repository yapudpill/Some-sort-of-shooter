package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.ICombatEntity;
import model.ingame.physics.DamageListener;

public class Gatling extends ProjectileWeaponModel implements ContinuousFireWeapon {
    private boolean isFiring = false;
    ModelTimer inBetweenTimer = new ModelTimer(0.048, false, this::fire, gameModel);
    int heat = 0;
    int maxheat = 100;
    ModelTimer coolingTimer = new ModelTimer(0.012, true, this::cooling,gameModel);
    public Gatling(ICombatEntity owner, GameModel gameModel) {

        super("Gatling", "Gatling", owner, gameModel, 0);
        coolingTimer.start();
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
        return true;
    }

    @Override
    public void fire() {
        if (isFiring && heat < maxheat) {
            setDirectionVector(getDirectionVector().rotate(Math.random() * 0.32 - 0.25));
            Projectile projectile = createProjectile();
            projectile.setPos(owner.getPos());
            projectile.getMovementHandler().setDirectionVector(this.directionVector);
            gameModel.attachAsUpdateable(projectile);
            gameModel.addEntity(projectile);
            inBetweenTimer.start();
            heat++;
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
