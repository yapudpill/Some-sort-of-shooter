package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;

public abstract class AbstractTrapEntity extends CollisionEntityModel {
    private final ICombatEntity owner;
    public AbstractTrapEntity(Coordinates pos, double width, double height, GameModel gameModel, ICombatEntity owner) {
        super(pos, width, height, gameModel);
        this.owner = owner;
        addCollisionListener((entity) -> {
            if (entity instanceof IVulnerableEntity) trigger((IVulnerableEntity) entity);
        });
    }

    protected abstract void trigger(IVulnerableEntity entity);
}
