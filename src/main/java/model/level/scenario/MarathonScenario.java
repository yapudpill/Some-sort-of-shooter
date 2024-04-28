package model.level.scenario;

import model.ingame.GameModel;
import model.ingame.entity.*;
import model.ingame.entity.IEnemy.IEnemyFactory;
import model.ingame.entity.IEntity.IEntityFactory;
import model.ingame.weapon.KnifeWeapon;
import model.ingame.weapon.PistolModel;
import model.ingame.weapon.RocketLauncher;
import model.ingame.weapon.ShotGun;
import model.ingame.weapon.WeaponModel.IWeaponFactory;

import java.util.List;

public class MarathonScenario implements IScenario {
    private static final List<IEnemyFactory> ENEMIES_BY_DIFF = List.of(
            WalkingEnemyModel::new,
            SmartEnemyModel::new,
            ExplodingEnemy::new
    );

    private static final List<IWeaponFactory> WEAPON_BY_POWER = List.of(
            KnifeWeapon::new,
            PistolModel::new,
            ShotGun::new,
            RocketLauncher::new
    );

    public MarathonScenario() {
    }

    // Values that should later be configurable from GUI
    public int initialWaveCooldown() {
        return 10;
    }

    public int initialWaveDuration() {
        return 30;
    }

    public List<IEnemyFactory> enemiesByDifficulty() {
        return ENEMIES_BY_DIFF;
    }

    public List<IWeaponFactory> weaponsByPower() {
        return WEAPON_BY_POWER;
    }

    public List<IEntityFactory> helperEntities() {
        return List.of(FirstAidKit::new);
    }


    @Override
    public IScenarioCursor createCursor(GameModel gameModel) {
        return new MarathonCursor(this, gameModel);
    }
}
