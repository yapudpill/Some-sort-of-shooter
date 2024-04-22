package util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Synchronise a set to a map, calling the addConsumer for each element in the
 * set that is not in the map, and the removeConsumer for each element in the
 * map that is not in the set.
 */
public class SetToMapSynchronisator {
    public static <K, V> void synchronise(Collection<K> collection, Map<K, V> map, Consumer<K> addConsumer, Consumer<K> removeConsumer) {
        for (K key : collection) {
            if (!map.containsKey(key)) {
                addConsumer.accept(key);
            }
        }
        for (K key : map.keySet()) {
            if (!collection.contains(key)) {
                removeConsumer.accept(key);
            }
        }
    }
}
