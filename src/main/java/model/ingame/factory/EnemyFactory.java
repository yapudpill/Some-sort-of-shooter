package model.ingame.factory;

import java.util.Iterator;
import java.util.List;

import model.ingame.entity.EnemyModel;

public class EnemyFactory {
    private static final List<Supplier<EnemyModel>> ENEMY_SUPPLIERS = List.of(); // lmabda expressions for enemy suppliers

    public static Iterator<Supplier<EnemyModel>> getEnemyListIterator(){
        return ENEMY_SUPPLIERS.iterator();
    }   

}
