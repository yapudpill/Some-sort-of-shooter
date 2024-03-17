package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.IEntity;

@FunctionalInterface
public interface WeaponFactory {
    WeaponModel createWeapon(IEntity owner, GameModel gameModel);
}
