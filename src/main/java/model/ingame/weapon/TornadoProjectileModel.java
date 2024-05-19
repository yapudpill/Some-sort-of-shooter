package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.CreatureModel;
import model.ingame.entity.PlayerModel;
import model.ingame.physics.DamageListener;
import model.ingame.physics.MovementHandler;
import util.Coordinates;

public class TornadoProjectileModel extends Projectile{
    public static final double TORNADO_SPEED = 8;
    public static final double TORNADO_WIDTH = 2.4;
    public static final double TORNADO_HEIGHT = 2.4;
    public static final int TORNADO_DAMAGE = 1;

    public TornadoProjectileModel(Coordinates pos, ProjectileWeaponModel source, GameModel gameModel) {
        super(pos, source, TORNADO_WIDTH, TORNADO_HEIGHT, TORNADO_DAMAGE, gameModel);
        movementHandler.setSpeed(TORNADO_SPEED);
        addCollisionListener(new DamageListener(TORNADO_DAMAGE));
        addCollisionListener(e-> {
            e.getInvolvedEntitiesList().forEach(ent -> {
                if(!(ent instanceof PlayerModel) && ent instanceof CreatureModel c) {
                    MovementHandler movementHandler = c.getMovementHandler();
                    movementHandler.setDirectionVector(this.movementHandler.getDirectionVector().add(movementHandler.getDirectionVector()));
                }
            });});
        addBlockedMovementListener(e -> despawn());
    }
}
