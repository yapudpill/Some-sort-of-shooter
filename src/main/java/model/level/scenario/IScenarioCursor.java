package model.level.scenario;

import model.ingame.entity.IEnemy.IEnemyFactory;
import model.ingame.entity.IEntity.IEntityFactory;
import util.IUpdateable;

import static model.ingame.weapon.WeaponModel.IWeaponFactory;

public interface IScenarioCursor extends IUpdateable {
    @Override
    void update(double delta);

    IWeaponFactory nextWeapon();

    IEnemyFactory nextEnemy();

    IEntityFactory nextMiscEntity();

    boolean isGameFinished();
}
