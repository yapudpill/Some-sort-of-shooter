package util;

import java.util.List;
import java.util.stream.IntStream;

public class MathTools {
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


    // TODO: optimize this, we compute similar things every time
    public static List<Double> getBinomialProbabilities(int n, double p) {
        return IntStream
                .range(0, n)
                .mapToDouble(k -> Math.pow(p, k) * Math.pow(1 - p, n - k) * MathTools.nCR(n, k))
                .boxed()
                .toList();
    }
}
