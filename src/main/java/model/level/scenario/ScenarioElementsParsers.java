package model.level.scenario;

import model.ingame.entity.*;
import model.ingame.weapon.KnifeWeapon;
import model.ingame.weapon.PistolModel;
import model.ingame.weapon.RocketLauncher;
import model.ingame.weapon.RubberWeapon;
import model.ingame.weapon.ShotGun;
import model.ingame.weapon.SimpleTrapPlacer;
import model.ingame.weapon.WeaponConstructor;

/**
 * Utility class that provides static methods to parse the names of the elements of a scenario and return the
 * corresponding constructors. Used for parsing scenarios from XML files
 */
public class ScenarioElementsParsers {

    public static WeaponConstructor parseWeapon(String name) {
        return switch(name) {
            case "Pistol" -> PistolModel::new;
            case "Knife" -> KnifeWeapon::new;
            case "RocketLauncher" -> RocketLauncher::new;
            case "ShotGun" -> ShotGun::new;
            case "RubberWeapon" -> RubberWeapon::new;
            case "TrapPlacer" -> SimpleTrapPlacer::new;
            default -> null;
        };
    }

    public static EntityConstructor parseEnemy(String name) {
        return switch(name) {
            case "ExplodingEnemy" -> ExplodingEnemy::new;
            case "SmartEnemy" -> SmartEnemyModel::new;
            case "WalkingEnemy" -> WalkingEnemyModel::new;
            case "LootEnemy" -> LootEnnemy::new;
            default -> null;
        };
    }

    public static EntityConstructor parseMisc(String name) {
        return switch(name) {
            case "Bandages" -> FirstAidKit::new;
            default -> null;
        };
    }
}
