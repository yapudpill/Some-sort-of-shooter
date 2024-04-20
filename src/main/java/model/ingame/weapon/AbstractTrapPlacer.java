package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.AbstractTrapEntity;
import model.ingame.entity.ICombatEntity;

public abstract class AbstractTrapPlacer extends WeaponModel {
    protected int trapCount;

    public AbstractTrapPlacer(String name, String identifier, GameModel gameModel, ICombatEntity owner, double coolDown, int trapCount) {
        super(name, identifier, gameModel, owner, coolDown);
        this.trapCount = trapCount;
    }

    protected abstract AbstractTrapEntity makeTrap();

    @Override
    public boolean attack() {
        if (trapCount != 0) {
            AbstractTrapEntity trap = makeTrap();
            gameModel.addEntity(trap);
            trapCount--;
            if (trapCount == 0) {
                getOwner().setWeapon(null);
            }
            return true;
        }
        return false;
    }
}
