package util;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class SetToMapSynchronisator {
    static public <K, V> void synchroniseSetToMap(Set<K> set, Map<K, V> map, Consumer<K> addConsumer, Consumer<K> removeConsumer) {
        // TODO: probably inefficient, is there an easier way to do this faster?
        for (K key : set) {
            if (!map.containsKey(key)) {
                addConsumer.accept(key);
            }
        }
        for (K key : map.keySet()) {
            if (!set.contains(key)) {
                removeConsumer.accept(key);
            }
        }
    }
}
