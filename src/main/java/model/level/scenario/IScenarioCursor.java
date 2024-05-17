package model.level.scenario;

import model.ingame.entity.EntityConstructor;
import model.ingame.weapon.WeaponConstructor;
import util.IUpdateable;

public interface IScenarioCursor extends IUpdateable {
    @Override
    void update(double delta);

    WeaponConstructor nextWeapon();

    EntityConstructor nextEnemy();

    EntityConstructor nextMiscEntity();

    boolean isGameFinished();
}
