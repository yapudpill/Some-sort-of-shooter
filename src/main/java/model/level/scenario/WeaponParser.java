package model.level.scenario;

import java.util.Map;

import model.ingame.weapon.KnifeWeapon;
import model.ingame.weapon.PistolModel;
import model.ingame.weapon.RocketLauncher;
import model.ingame.weapon.RubberWeapon;
import model.ingame.weapon.ShotGun;
import model.ingame.weapon.SimpleTrapPlacer;
import model.ingame.weapon.WeaponConstructor;

public class WeaponParser {
    public static final Map<String, WeaponConstructor> AVAILABLE_WEAPONS = Map.of(
            "Pistol", PistolModel::new,
            "Knife", KnifeWeapon::new,
            "RocketLauncher", RocketLauncher::new,
            "ShotGun", ShotGun::new,
            "RubberWeapon", RubberWeapon::new,
            "TrapPlacer", SimpleTrapPlacer::new
    );

    public static WeaponConstructor parseWeapon(String weaponName) {
        return AVAILABLE_WEAPONS.get(weaponName);
    }
}
