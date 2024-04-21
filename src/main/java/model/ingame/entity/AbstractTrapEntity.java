package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;

public abstract class AbstractTrapEntity extends CollisionEntityModel {
    AbstractTrapEntity(Coordinates pos, double width, double height, GameModel gameModel, ICombatEntity owner) {
        super(pos, width, height, gameModel);
        addCollisionListener(e -> {
            for (ICollisionEntity entity: e.getInvolvedEntitiesList()) {
                if (entity != owner && entity instanceof IVulnerableEntity vulnerableEntity) {
                    trigger(vulnerableEntity);
                    break;
                }
            }
        });
    }

    protected abstract void trigger(IVulnerableEntity entity);
}
