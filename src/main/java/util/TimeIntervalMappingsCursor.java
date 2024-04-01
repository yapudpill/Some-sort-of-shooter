package util;


import model.ingame.IUpdateable;

import java.util.Iterator;

public class TimeIntervalMappingsCursor<V> implements IUpdateable {
    private Iterator<Pair<Long, V>> associationsIterator;
    private final TimeIntervalMappings<V> timeIntervalMappings;
    private Pair<Long, V> currentTimeValuePair;
    private long currentTime = 0;
    public TimeIntervalMappingsCursor(TimeIntervalMappings<V> timeIntervalMappings) {
        this.timeIntervalMappings = timeIntervalMappings;
        initIterator();
    }

    private void initIterator() {
        associationsIterator = timeIntervalMappings.getMappings().iterator();
        if (associationsIterator.hasNext()) {
            this.currentTimeValuePair = associationsIterator.next();
        }
    }

    public void advanceTime() {
        currentTime++;
        if (currentTimeValuePair != null && currentTime == currentTimeValuePair.first()) { // Reached end of interval
            if (associationsIterator.hasNext()) {
                currentTimeValuePair = associationsIterator.next();
            } // Reached end of map
            else switch (timeIntervalMappings.getEndReachedBehaviour()) {
                case FINITE:
                    currentTimeValuePair = null;
                case INFINITE:
                    // Do nothing, keep using the last value
                    break;
                case LOOPING:
                    reset();
                    break;
            }
        }
    }

    public V getCurrentValue() {
        if (currentTimeValuePair != null) return currentTimeValuePair.second();
        else return null;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void reset() {
        initIterator();
        currentTime = 0;
    }

    @Override
    public void update() {
        advanceTime();
    }
}