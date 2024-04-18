package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;
import util.IUpdateable;

public class AttachedDamageZoneEntity extends CollisionEntityModel implements IUpdateable {
    private final CombatEntityModel attacker;
    private final double shift;

    public AttachedDamageZoneEntity(Coordinates pos, double width, double height, double shift, GameModel gameModel, CombatEntityModel attacker, int damage) {
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

    @Override
    public void update(double delta) {
        Coordinates pos = new Coordinates(attacker.getPos());
        setPos(pos.add(attacker.getMovementHandler().getDirectionVector().multiply(shift)));
    }
}
