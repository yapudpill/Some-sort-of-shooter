package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.physics.DamageListener;
import util.Coordinates;

public class BlackHoleProjectile extends Projectile{
    public static final double COLLAPSE_DELAY = 0.5;
    private final ModelTimer collapseTimer;
    private final ModelTimer despawnTimer;
    private boolean collapsed = false;

    public BlackHoleProjectile(Coordinates pos, ProjectileWeaponModel source,
            GameModel gameModel) {
        super(pos, source, 5, 5, 1, gameModel);
        despawnTimer = new ModelTimer(3, false, this::despawn, gameModel);
        movementHandler.setSpeed(10);
        addCollisionListener(new DamageListener(damage));
        addBlockedMovementListener(e -> {
            if(!collapsed) collapse();
        });
        collapseTimer = new ModelTimer(COLLAPSE_DELAY, false, () -> {
            collapse();
            despawnTimer.start();
        }, gameModel);
    }

    public void collapse() {
        // add blackhole zones in the 4 by 4 grid centered at the blackhole
        for(int i = -2; i <= 2; i++) {
            for(int j = -2; j <= 2; j++) {
                Coordinates npos = new Coordinates(pos.x() + i, pos.y() + j);
                if(gameModel.getMapModel().isOutOfBounds(npos)) continue;
                gameModel.addEntity(new BlackHoleZone(npos, gameModel, pos));
            }
        }
        movementHandler.setDirectionVector(Coordinates.ZERO);
        collapsed = true;
    }

    public void update(double delta) {
        movementHandler.update(delta);
    }

    public void startCollapseTimer() {
        collapseTimer.start();
    }

}
