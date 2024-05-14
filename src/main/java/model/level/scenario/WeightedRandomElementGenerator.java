package model.level.scenario;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class WeightedRandomElementGenerator<E> {
    private final Random rng = new Random();

    public abstract Collection<E> getAllowedElements();

    private Map<Double, E> elementRates; // Rate in probability of spawn per second
    private double tickLength;

    public WeightedRandomElementGenerator(double tickLength) {
        this.tickLength = tickLength;
    }

    public void setElementRates(Map<Double, E> rates) {
        this.elementRates = rates;
    }

    public Set<E> nextElements(double delta) {
        Set<E> res = new HashSet<>();
        for (Map.Entry<Double, E> entry : elementRates.entrySet()) {
            double pick = rng.nextDouble();
            if (pick < entry.getKey() * delta) {
                res.add(entry.getValue());
            }
        }
        return res;
    }
}