package util;

import java.util.TreeMap;
import java.util.function.BinaryOperator;

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
