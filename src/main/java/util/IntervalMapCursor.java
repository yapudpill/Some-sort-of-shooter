package util;

import java.util.function.BinaryOperator;

public class IntervalMapCursor<K extends Comparable<K>, V> {
    private final IntervalMap<K, V> intervalMap;
    private final BinaryOperator<K> add;
    protected K currentKey;

    public IntervalMapCursor(IntervalMap<K, V> intervalMap, BinaryOperator<K> add) {
        this.intervalMap = intervalMap;
        this.add = add;
        this.currentKey = intervalMap.getZero();
    }

    public void advance(K delta) {
        if (currentKey == null) { // The map is finite and fully consumed
            return;
        }

        currentKey = add.apply(delta, currentKey);
        if (currentKey.compareTo(intervalMap.lastKey()) > 0) { // Reached end of map
            switch (intervalMap.getEndReachedBehaviour()) {
                case FINITE   -> currentKey = null;
                case INFINITE -> {} // Keep using the last value
                case LOOPING  -> currentKey = intervalMap.loop(currentKey);
            };
        }
    }

    public V getCurrentValue() {
        if (currentKey == null) {
            return null;
        }
        return intervalMap.getInInterval(currentKey);
    }
}