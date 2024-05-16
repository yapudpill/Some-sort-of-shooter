package model.level.scenario;

import java.util.List;

import model.ingame.GameModel;
import model.ingame.entity.EntityConstructor;
import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.FirstAidKit;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.weapon.KnifeWeapon;
import model.ingame.weapon.PistolModel;
import model.ingame.weapon.RocketLauncher;
import model.ingame.weapon.ShotGun;
import model.ingame.weapon.WeaponConstructor;

public class MarathonScenario implements IScenario {
    private static final List<EntityConstructor> ENEMIES_BY_DIFF = List.of(
            WalkingEnemyModel::new,
            SmartEnemyModel::new,
            ExplodingEnemy::new
    );

    private static final List<WeaponConstructor> WEAPON_BY_POWER = List.of(
            KnifeWeapon::new,
            PistolModel::new,
            ShotGun::new,
            RocketLauncher::new
    );

    public MarathonScenario() {
    }

    // Values that may later be configured in the GUI
    public int initialWaveCooldown() {
        return 5;
    }

    public int initialWaveDuration() {
        return 20;
    }

    public List<EntityConstructor> enemiesByDifficulty() {
        return ENEMIES_BY_DIFF;
    }

    public List<WeaponConstructor> weaponsByPower() {
        return WEAPON_BY_POWER;
    }

    public List<EntityConstructor> helperEntities() {
        return List.of(FirstAidKit::new);
    }


    @Override
    public IScenarioCursor createCursor(GameModel gameModel) {
        return new MarathonCursor(this, gameModel);
    }
}
