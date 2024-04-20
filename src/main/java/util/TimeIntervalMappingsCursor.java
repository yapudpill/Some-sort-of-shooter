package util;


import java.util.Iterator;

public class TimeIntervalMappingsCursor<V> {
    private Iterator<Pair<Double, V>> associationsIterator;
    private final TimeIntervalMappings<V> timeIntervalMappings;
    private Pair<Double, V> currentTimeValuePair;
    private double currentTime = 0;

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

    public void advanceTime(double deltaT) {
        currentTime += deltaT;
        if (currentTimeValuePair != null && currentTime >= currentTimeValuePair.first()) { // Reached end of interval
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

    public double getCurrentTime() {
        return currentTime;
    }

    public void reset() {
        initIterator();
        currentTime = 0;
    }
}