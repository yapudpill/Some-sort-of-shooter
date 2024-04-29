package model.level.scenario;

import model.ingame.weapon.WeaponModel;

import java.util.Collection;

public class WeaponGenerator extends WeightedRandomElementGenerator<WeaponModel.IWeaponFactory> {
    public WeaponGenerator(double tickRate) {
        super(tickRate);
    }

    @Override
    public Collection<WeaponModel.IWeaponFactory> getAllowedElements() {
        return WeaponParser.AVAILABLE_WEAPONS.values();
    }
}
