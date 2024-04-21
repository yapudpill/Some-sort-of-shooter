package model.level.scenario;

import model.ingame.weapon.*;

import java.util.Map;

public class WeaponParser {
    public static final Map<String, WeaponFactory> AVAILABLE_WEAPONS = Map.of(
            "Pistol", PistolModel::new,
            "Knife", KnifeWeapon::new,
            "RocketLauncher", RocketLauncher::new,
            "ShotGun", ShotGun::new,
            "RubberWeapon", RubberWeapon::new,
            "TrapPlacer", SimpleTrapPlacer::new
    );

    public static WeaponFactory parseWeapon(String weaponName) {
        return AVAILABLE_WEAPONS.get(weaponName);
    }
}
