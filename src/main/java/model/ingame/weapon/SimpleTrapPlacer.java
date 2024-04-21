package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.AbstractTrapEntity;
import model.ingame.entity.ICombatEntity;
import model.ingame.entity.SimpleTrap;

class SimpleTrapPlacer extends AbstractTrapPlacer {
    private static final String NAME = "Simple Trap Placer (%d)";

    SimpleTrapPlacer(ICombatEntity owner, GameModel gameModel) {
        super("Simple Trap placer", "simple_trap_placer", gameModel, owner, 0.3, 3);
    }

    @Override
    protected AbstractTrapEntity makeTrap() {
        return new SimpleTrap(getOwner().getPos(), gameModel, getOwner());
    }

    @Override
    public String getName() {
        return String.format(NAME, trapCount);
    }
}
