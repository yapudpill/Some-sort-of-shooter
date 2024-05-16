package model.level.scenario;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WeightedRandomGenerator<E> {
    private final Random rng = new Random();

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