package model.level.scenario;

import model.ingame.GameModel;
import model.ingame.entity.*;
import model.ingame.weapon.*;

import java.util.List;

/**
 * A marathon scenario. Doesn't contain a lot of information as it is supposed to be fixed, so the {@link MarathonCursor}
 * handles the dynamic aspect of the marathon mode
 */
public class MarathonScenario implements IScenario {
    private static final List<EntityConstructor> ENEMIES_BY_DIFF = List.of(
            WalkingEnemyModel::new,
            SmartEnemyModel::new,
            ExplodingEnemy::new
    );

    private static final List<WeaponConstructor> WEAPON_BY_POWER = List.of(
            KnifeWeapon::new,
            SimpleTrapPlacer::new,
            PistolModel::new,
            RubberWeapon::new,
            ShotGun::new,
            RocketLauncher::new,
            GravityGun::new
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
