package model.ingame.weapon;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.Timer;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.IEntity;
import model.ingame.physics.PhysicsEngineModel;
import util.ModelTimer;

public abstract class ProjectileWeaponModel {
    protected IEntity owner;
    protected final PhysicsEngineModel physicsEngine;
    protected final String name;
    protected int coolDown;
    protected ModelTimer coolDownTimer;
    protected boolean isCoolingDown;
    protected Coordinates directionVector;
    protected GameModel gameModel;

    public ProjectileWeaponModel(String name, GameModel gameModel, IEntity owner, int coolDown) {
        this.name = name;
        this.physicsEngine = gameModel.getPhysicsEngine();
        this.gameModel = gameModel;
        this.owner = owner;
        this.coolDown = coolDown;
        this.isCoolingDown = false;
        coolDownTimer = new ModelTimer(coolDown, () -> {
            isCoolingDown = false;
        }, gameModel);
        coolDownTimer.setRepeats(false);
    }

    public String getName() {
        return name;
    }

    public abstract IProjectile createProjectile();

    public void shoot() {
        if (isCoolingDown) {
            System.out.println("Weapon is cooling down. Cannot shoot.");
            return;
        }

        isCoolingDown = true;
        IProjectile projectile = createProjectile();
        projectile.setPos(new Coordinates(owner.getPos()));
        projectile.setSourceWeapon(this);
        System.out.println("Direction vector: " + directionVector.toString());
        projectile.getMovementHandler().setDirectionVector(this.directionVector);
        gameModel.attachAsUpdateable(projectile);
        gameModel.addEntity(projectile);
        coolDownTimer.start();
    }

    public void setOwner(IEntity owner) {
        this.owner = owner;
    }

    public IEntity getOwner() {
        return owner;
    }

    public int getCoolDown() {
        return coolDown;
    }


    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public boolean isCoolingDown() {
        return isCoolingDown;
    }

    public void setCoolingDown(boolean isCoolingDown) {
        this.isCoolingDown = isCoolingDown;
    }

    public Coordinates getDirectionVector() {
        return directionVector;
    }

    public void setDirectionVector(Coordinates directionVector) {
        this.directionVector = directionVector;
    }
}
