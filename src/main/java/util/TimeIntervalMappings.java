package util;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeIntervalMappings<V> {
    public TimeIntervalMappings(EndReachedBehaviour endReachedBehaviour) {
        this.endReachedBehaviour = endReachedBehaviour;
    }

    public enum EndReachedBehaviour {
        FINITE, // Values for time > last interval are not defined
        INFINITE, // Values for time > last interval are the last value
        LOOPING // Loop over the values
    }

    private final LinkedList<Pair<Double, V>> mappings = new LinkedList<Pair<Double, V>>();
    private final EndReachedBehaviour endReachedBehaviour;

    public V getAssociatedValue(Long time) {
        if (time < 0) throw new IllegalArgumentException("Time has to be > 0.");
        if (mappings.isEmpty()) throw new IllegalArgumentException("No intervals defined.");
        if (time >= mappings.getLast().first()) { // Do this check first, since getting the last element of LinkedList is O(1)
            switch (endReachedBehaviour) {
                case FINITE:
                    return null;
                case INFINITE:
                    return mappings.getLast().second();
                case LOOPING:
                    time %= mappings.getLast().first().longValue();
                    break;
            }
        }
        for (Pair<Double, V> timeValue : mappings) {
            if (time < timeValue.first()) { // return the first interval that stops after time
                return timeValue.second();
            }
        }
        return null;
    }

    public void addNextAssociation(double endTime, V value) {
        if (!mappings.isEmpty() && endTime < mappings.getLast().first())
            throw new IllegalArgumentException("endTime has to be lower than last added interval");
        mappings.add(new Pair<Double,V>(endTime, value));
    }


    public LinkedList<Pair<Double, V>> getMappings() {
        return mappings;
    }

    public List<V> values() {
        return mappings.stream().map(Pair::second).collect(Collectors.toList());
    }

    public double getDuration() {
        return mappings.getLast().first();
    }

    public EndReachedBehaviour getEndReachedBehaviour() {
        return endReachedBehaviour;
    }
}

