package model.ingame.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.ingame.Coordinates;
import model.ingame.physics.CollisionEvent;
import model.ingame.physics.CollisionListener;
import model.ingame.physics.IMovementHandler;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.physics.PhysicsEngineModel;
import model.ingame.weapon.BulletsModel;
import model.ingame.weapon.IProjectile;

public class PlayerModel extends CollisionEntityModel implements ICombatEntity, IVulnerableEntity {
    private final PhysicsEngineModel physicsEngine;
    private final MovementHandlerModel<PlayerModel> movementHandler;
    private final List<IProjectile> projectilesShot = new ArrayList<>();
    private int health;
    private int maxHealth;

    public PlayerModel(PhysicsEngineModel physicsEngine) {
        super(Coordinates.ZERO, 0.5, 0.5);
        this.physicsEngine = physicsEngine;
        this.movementHandler = new MovementHandlerModel<PlayerModel>(this, physicsEngine);
        movementHandler.setSpeed(0.2);
    }

    @Override
    public IMovementHandler getMovementHandler() {
        return movementHandler;
    }

    @Override
    public void attack() {
        IProjectile bullet = new BulletsModel(getPos(), physicsEngine);
        bullet.getMovementHandler().setDirectionVector(movementHandler.getDirectionVector());
        projectilesShot.add(bullet);
    }

    @Override
    public int getDamage() {
        return BulletsModel.BULLET_DAMAGE;
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }
    
    @Override
    public void update(){
        movementHandler.update();
        projectilesShot.forEach(projectile -> {
            if(!projectile.isActive()) projectilesShot.remove(projectile);
            projectile.update();
        });
    }
}
