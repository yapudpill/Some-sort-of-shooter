package util;

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
}
