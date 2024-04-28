package model.level.scenario;

import java.util.*;

public abstract class WeightedRandomElementGenerator<E> {
    private final Random rng = new Random();

    public abstract Collection<E> getAllowedElements();

    private Map<Double, E> elementRates; // Rate in probability of spawn per second

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