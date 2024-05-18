package util;

import java.util.function.BinaryOperator;

/**
 * A cursor to navigate through an {@link IntervalMap}.
 * <p>
 * Very generic, so it needs a binary operator corresponding to the addition for the type K.
 *
 * @param <K> The type of the keys, i.e. the unit of the intervals used to index the map
 * @param <V> The type of the values
 */
public class IntervalMapCursor<K extends Comparable<K>, V> {
    private final IntervalMap<K, V> intervalMap;
    private final BinaryOperator<K> add;
    protected K currentKey;
    private boolean hasChanged;
    private boolean hasLooped;

    public IntervalMapCursor(IntervalMap<K, V> intervalMap, BinaryOperator<K> add) {
        this.intervalMap = intervalMap;
        this.add = add;
        this.currentKey = intervalMap.getZero();
    }

    public void advance(K delta) {
        V lastValue = getCurrentValue();
        this.hasLooped = false;
        this.hasChanged = false;
        if (currentKey == null) { // The map is finite and fully consumed
            return;
        }

        currentKey = add.apply(delta, currentKey);
        if (!intervalMap.isEmpty() && currentKey.compareTo(intervalMap.lastKey()) > 0) { // Reached end of map
            switch (intervalMap.getEndReachedBehaviour()) {
                case FINITE   -> currentKey = null;
                case INFINITE -> {} // Keep using the last value
                case LOOPING  -> {
                    this.currentKey = intervalMap.loop(currentKey);
                    this.hasLooped = true;
                }
            }
        }

        this.hasChanged = lastValue != getCurrentValue();
    }

    public V getCurrentValue() {
        if (currentKey == null) {
            return null;
        }
        return intervalMap.getInInterval(currentKey);
    }

    public boolean hasChanged() {
        return hasChanged;
    }

    public boolean hasLooped() {
        return hasLooped;
    }

    public boolean hasEnded() {
        return currentKey == null;
    }
}