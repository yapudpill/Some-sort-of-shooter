package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.IUpdateable;

public class KnifeZoneEntity extends CollisionEntityModel implements IUpdateable {
    CombatEntityModel attacker;
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
                    System.out.println("ouch");
                }
            }
        });
    }

    // Used for rendering
    public Coordinates getDirection() {
        if (!attacker.getMovementHandler().isMoving()) return attacker.getWeapon().getDirectionVector().normalize();
        else return attacker.getMovementHandler().getDirectionVector().normalize();
    }

    @Override
    public void update() {
        Coordinates pos = new Coordinates(attacker.getPos());
        setPos(pos.add(getDirection().multiply(shift)));
    }
}
