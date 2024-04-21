package model.level.scenario;

import model.ingame.weapon.WeaponFactory;

import java.util.Collection;

public class WeaponGenerator extends WeightedRandomElementGenerator<WeaponFactory> {
    public WeaponGenerator(double tickRate) {
        super(tickRate);
    }

    @Override
    public Collection<WeaponFactory> getAllowedElements() {
        return WeaponParser.AVAILABLE_WEAPONS.values();
    }
}
