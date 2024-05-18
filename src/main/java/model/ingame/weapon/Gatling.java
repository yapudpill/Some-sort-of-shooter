package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.ICombatEntity;
import model.ingame.physics.DamageListener;

public class Gatling extends ProjectileWeaponModel {
    private boolean isFiring = false;
    public Gatling(  ICombatEntity owner, GameModel gameModel) {
        super("Gatling", "Gatling", owner, gameModel, 0.5);
    }
    ModelTimer inBetweenTimer = new ModelTimer(0.048, false, () -> {}, gameModel);

    @Override
    public Projectile createProjectile() {
        return new BulletsModel(owner.getPos(), this, gameModel) {{
            addCollisionListener(new DamageListener(damage, e -> despawn()));
        }};
    }

    @Override
    public boolean attack() {
        if (isCoolingDown()) {
            return false;
        }
        if (!isFiring){
            isFiring = true;
            fireStart();
        } else {
            isFiring = false;
        }
        return true;
    }

    public void fireStart(){
        setDirectionVector(getDirectionVector().rotate(Math.random() * 0.5 - 0.25));
        fire();
        if (isFiring && !inBetweenTimer.isRunning()){
            System.out.println("test");
            fireStart();
        }
    }

    public void fire() {
        Projectile projectile = createProjectile();
        projectile.setPos(owner.getPos());
        projectile.getMovementHandler().setDirectionVector(this.directionVector);
        gameModel.attachAsUpdateable(projectile);
        gameModel.addEntity(projectile);
        inBetweenTimer.start();
    }
}
