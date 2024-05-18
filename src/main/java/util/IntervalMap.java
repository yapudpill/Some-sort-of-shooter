package util;

import java.util.TreeMap;
import java.util.function.BinaryOperator;

/**
 * A map from intervals to values. Useful for animations, scenarios...
 * <p>
 * Very generic, so it needs a zero value and a modulo operation defined for the type K.
 * <p>
 * For example, if the map contains the intervals [0, 10) and [10, 20) with values 1 and 2,then the value for key 5 is 1
 * and the value for key 15 is 2.
 *
 * @param <K> The type of the keys, i.e. the unit of the intervals used to index the map
 * @param <V> The type of the values
 */
public class IntervalMap<K extends Comparable<K>, V> extends TreeMap<K, V> {

    /** Start of the first interval */
    private final K zero;
    private final BinaryOperator<K> mod;
    private final EndReachedBehaviour endReachedBehaviour;

    public IntervalMap(K zero, BinaryOperator<K> mod, EndReachedBehaviour endReachedBehaviour) {
        if (zero == null) {
            throw new IllegalArgumentException("Zero cannot be null.");
        }
        this.zero = zero;
        this.mod = mod;
        this.endReachedBehaviour = endReachedBehaviour;
    }

    public K loop(K delta) {
        return mod.apply(delta, lastKey());
    }

    public V getInInterval(K key) {
        if (isEmpty() || key.compareTo(zero) < 0) {
            return null;
        }
        if (key.compareTo(lastKey()) > 0) {
            return switch (endReachedBehaviour) {
                case FINITE -> null;
                case INFINITE -> lastEntry().getValue();
                case LOOPING -> getInInterval(loop(key));
            };
        }
        return ceilingEntry(key).getValue();
    }

    public K getZero() {
        return zero;
    }

    public EndReachedBehaviour getEndReachedBehaviour() {
        return endReachedBehaviour;
    }
}
