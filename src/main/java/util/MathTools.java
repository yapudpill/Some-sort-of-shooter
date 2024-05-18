package util;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Some math tools.
 */
public class MathTools {
    /**
     * @param n The number of elements
     * @param k The number of elements to choose
     * @return The number of ways to choose k elements from n elements
     */
    public static int nCR(int n, int k) {
        int a = 1;
        for (int i = n; i > n - k; i--) {
            a *= i;
        }
        int b = 1;
        for (int i = 2; i <= k; i++) {
            b *= i;
        }

        return a / b;
    }


    /**
     * Generates the probabilities of getting k successes for k in [0, n) in a binomial distribution.
     *
     * @param n The number of elements
     * @param p The probability of success
     * @return The probabilities of getting k successes for k in [0, n)
     */
    // TODO: optimize this, we compute similar things every time
    public static List<Double> getBinomialProbabilities(int n, double p) {
        return IntStream
                .range(0, n)
                .mapToDouble(k -> Math.pow(p, k) * Math.pow(1 - p, n - k) * MathTools.nCR(n, k))
                .boxed()
                .toList();
    }
}
