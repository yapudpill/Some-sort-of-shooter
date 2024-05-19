package model.level.scenario;

import model.ingame.entity.EntityConstructor;
import model.ingame.weapon.WeaponConstructor;
import util.IUpdateable;

/**
 * A scenario cursor. Allows to iterate over a scenario and provides the entities and weapons that should be spawned at
 * a specific time.
 */
public interface IScenarioCursor extends IUpdateable {
    @Override
    void update(double delta);

    WeaponConstructor nextWeapon();

    EntityConstructor nextEnemy();

    EntityConstructor nextMiscEntity();

    boolean isGameFinished();
}
