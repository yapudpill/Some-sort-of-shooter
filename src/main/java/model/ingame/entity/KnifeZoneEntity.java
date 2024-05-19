package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;
import util.IUpdateable;

/**
 * A knife zone entity that damages entities that come into contact with it. Despawns when it hits an entity.
 * The knife should handle the despawning of the knife zone entity.
 * <p>
 * The zone stays in the direction of the attacker's weapon or movement.
 */
public class KnifeZoneEntity extends CollisionEntityModel implements IUpdateable {
    private final CombatEntityModel attacker;
    private final double shift;

    public KnifeZoneEntity(Coordinates pos, double width, double height, double shift, GameModel gameModel, CombatEntityModel attacker, int damage) {
        super(pos, width, height, gameModel);
        this.attacker = attacker;
        this.shift = shift;

        addCollisionListener(e -> {
            for (ICollisionEntity entity: e.getInvolvedEntitiesList()) {
                if (entity != attacker && entity instanceof IVulnerableEntity vulnerableEntity) {
                    vulnerableEntity.takeDamage(damage);
                    despawn();
                }
            }
        });
    }

    // Used for rendering
    public Coordinates getDirection() {
        if (attacker.getWeapon() != null && !attacker.getMovementHandler().isMoving()) {
            Coordinates direction = attacker.getWeapon().getDirectionVector();
            if (direction != null) return direction.normalize();
        }
        return attacker.getMovementHandler().getDirectionVector().normalize();
    }

    @Override
    public void update(double delta) {
        setPos(attacker.getPos().add(getDirection().multiply(shift)));
    }
}
