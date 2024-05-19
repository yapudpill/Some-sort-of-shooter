package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;

/**
 * A simple trap that deals damage to entities that step on it.
 */
public class SimpleTrap extends AbstractTrapEntity {
    private static final int DAMAGE = 20;
    private static final double WIDTH = 1;
    private static final double HEIGHT = 1;

    public SimpleTrap(Coordinates pos, GameModel gameModel, ICombatEntity owner) {
        super(pos, WIDTH, HEIGHT, gameModel, owner);
    }

    @Override
    protected void trigger(IVulnerableEntity entity) {
        entity.takeDamage(DAMAGE);
        despawn();
    }
}
