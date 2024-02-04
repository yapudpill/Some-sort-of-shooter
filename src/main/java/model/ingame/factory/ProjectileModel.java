package model.ingame.factory;

import java.util.Iterator;
import java.util.List;

public class ProjectileModel {
    public static final List<Supplier<ProjectileModel>> PROJECTILE_SUPPLIERS = List.of(
        // add projectile suppliers here
    );

    public static Iterator<Supplier<ProjectileModel>> getProjectileListIterator(){
        return PROJECTILE_SUPPLIERS.iterator();
    }
}
