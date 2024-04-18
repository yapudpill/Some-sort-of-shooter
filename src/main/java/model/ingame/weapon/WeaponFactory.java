package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;

@FunctionalInterface
public interface WeaponFactory {
    WeaponModel createWeapon(ICombatEntity owner, GameModel gameModel);
}
