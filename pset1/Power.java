// Class to compute x^n using recursion with optimization for even exponents
class Power {

    // public static double power (double x, int n) {
    //     if (n == 0) return 1.0;
    //     else if (n > 0) return x * power(x, n-1);
    //     else return 1.0 / power(x, -n);
    // }

    public static double power(double x, int n) {
        // Base case remains the same
        if (n == 0) return 1.0;

        else if (n > 0) {
            // If exponent is even, compute power(x, n/2) once and square it
            if (n % 2 == 0) {
                double root = power(x, n / 2);
                return root * root;
            } else {
                // same as before
                return x * power(x, n - 1);
            }
        }

        // same as before
        else return 1.0 / power(x, -n);
    }

    // Test the power method with sample inputs
    public static void main(String[] args) {
        System.out.println(power(2.0, 3));   // 8.0
        System.out.println(power(2.0, -2));  // 0.25
        System.out.println(power(5.0, 0));   // 1.0
    }
}
