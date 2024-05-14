package model.level.scenario;

import java.util.*;

public abstract class WeightedRandomElementGenerator<E> {
    private final Random rng = new Random();

    public abstract Collection<E> getAllowedElements();

    private Map<E, Double> elementRates; // Rate in probability of spawn per second

    public void setElementRates(Map<E, Double> rates) {
        this.elementRates = rates;
    }

    public Set<E> nextElements(double delta) {
        if (elementRates == null) return Set.of();
        Set<E> res = new HashSet<>();
        for (Map.Entry<E, Double> entry : elementRates.entrySet()) {
            double pick = rng.nextDouble();
            if (pick < entry.getValue() * delta) {
                res.add(entry.getKey());
            }
        }
        return res;
    }
}