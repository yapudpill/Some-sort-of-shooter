package main.java.model.ingame.factory;

import java.util.Iterator;
import java.util.List;

import main.java.model.ingame.weapon.WeaponModel;

public class WeaponFactory {
    private static final List<Supplier<WeaponModel>> WEAPON_SUPPLIERS = List.of(
        // add weapon suppliers here
    );

    public static Iterator<Supplier<WeaponModel>> getWeaponListIterator(){
        return WEAPON_SUPPLIERS.iterator();
    }

}
