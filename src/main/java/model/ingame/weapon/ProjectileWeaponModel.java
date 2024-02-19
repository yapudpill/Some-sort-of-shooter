package model.ingame.weapon;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import model.ingame.Coordinates;
import model.ingame.entity.IEntity;
import model.ingame.physics.PhysicsEngineModel;

public abstract class ProjectileWeaponModel {
    protected IEntity owner;
    protected final List<IProjectile> shotProjectiles = new ArrayList<>();
    protected final PhysicsEngineModel physicsEngine;
    protected final String name;
    protected int coolDown;
    protected Timer coolDownTimer;
    protected boolean isCoolingDown;

    public ProjectileWeaponModel(String name, PhysicsEngineModel physicsEngine, IEntity owner, int coolDown) {
        this.name = name;
        this.physicsEngine = physicsEngine;
        this.owner = owner;
        this.coolDown = coolDown;
        this.isCoolingDown = false;
        coolDownTimer = new Timer(coolDown, e -> {
            isCoolingDown = false;
        });
        coolDownTimer.setRepeats(false);
    }

    public String getName() {
        return name;
    }

    public abstract IProjectile createProjectile();

    public void shoot(Coordinates directionVector) {
        if(isCoolingDown) return;
        isCoolingDown = true;
        IProjectile projectile = createProjectile();
        projectile.setPos(owner.getPos());
        projectile.setSourceWeapon(this);
        System.out.println("Direction vector: " + directionVector.toString());
        projectile.getMovementHandler().setDirectionVector(new Coordinates(directionVector));
        shotProjectiles.add(projectile);
        coolDownTimer.start();
    }

    public List<IProjectile> getShotProjectiles() {
        return shotProjectiles;
    }

    public void update() {
        for (IProjectile projectile : shotProjectiles) {
            projectile.update();
        }
        shotProjectiles.removeIf(projectile -> !projectile.isActive());

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

    public List<IProjectile> getProjectiles() {
        return shotProjectiles;
    }

}
