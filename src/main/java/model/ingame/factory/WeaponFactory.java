package model.ingame.factory;

import java.util.Iterator;
import java.util.List;

import model.ingame.weapon.WeaponModel;

public class WeaponFactory {
    private static final List<Supplier<WeaponModel>> WEAPON_SUPPLIERS = List.of(
        // add weapon suppliers here
    );

    public static Iterator<Supplier<WeaponModel>> getWeaponListIterator(){
        return WEAPON_SUPPLIERS.iterator();
    }

}
