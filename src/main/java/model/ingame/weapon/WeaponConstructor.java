package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;

/**
 * Functional interface for weapon constructors.
 */
@FunctionalInterface
public interface WeaponConstructor {
    WeaponModel createWeapon(ICombatEntity owner, GameModel gameModel);
}
