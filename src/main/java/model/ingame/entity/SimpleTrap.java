package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;

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
