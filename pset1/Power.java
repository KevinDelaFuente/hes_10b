// Class to compute x^n using recursion with optimization for even exponents
class Power {

    // public static double power (double x, int n) {
    //     if (n == 0) return 1.0;
    //     else if (n > 0) return x * power(x, n-1);
    //     else return 1.0 / power(x, -n);
    // }

    static int counter = 0; // David gave us permission to break the rules ;)

    public static double power(double x, int n) {
        counter++; // Increment the counter each time the method is called
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


    // Test the power method and counter for various values of n
    public static void main(String[] args) {
        double result;
        double x = 2.0;

        counter = 0;
        result = power(x, -2);
        System.out.printf("power(%.1f, -2) = %.1f, counter = %d\n", x, result, counter);

        counter = 0;
        result = power(x, 0);
        System.out.printf("power(%.1f, 0) = %.1f, counter = %d\n", x, result, counter);

        counter = 0;
        result = power(x, 3);
        System.out.printf("power(%.1f, 3) = %.1f, counter = %d\n", x, result, counter);

        counter = 0;
        result = power(x, 5);
        System.out.printf("power(%.1f, 5) = %.1f, counter = %d\n", x, result, counter);

        counter = 0;
        result = power(x, 6);
        System.out.printf("power(%.1f, 6) = %.1f, counter = %d\n", x, result, counter);

        counter = 0;
        result = power(x, 8);
        System.out.printf("power(%.1f, 8) = %.1f, counter = %d\n", x, result, counter);

        counter = 0;
        result = power(x, 9);
        System.out.printf("power(%.1f, 9) = %.1f, counter = %d\n", x, result, counter);

        counter = 0;
        result = power(x, 10);
        System.out.printf("power(%.1f, 10) = %.1f, counter = %d\n", x, result, counter);

        counter = 0;
        result = power(x, 1024);
        System.out.printf("power(%.1f, 1024) = %.1f, counter = %d\n", x, result, counter); //
    }
}

// b. This one was a bit mind blowing to me, at first I thought it was n/2 but the iterations are also going to be even
// so the optimized implementation will call the recursive method only log2(n) + 2 times. 
// One for the base case, one for the initial call, and then log2(n) as it calls on the recursive path at each half.
// In the case of 1024, it's 12.
// power(foobar, 1024): 1
// power(foobar, 512): 2
// power(foobar, 256): 3
// power(foobar, 128): 4
// power(foobar, 64): 5
// power(foobar, 32): 6
// power(foobar, 16): 7
// power(foobar, 8): 8
// power(foobar, 4): 9
// power(foobar, 2): 10
// power(foobar, 1): 11
// power(foobar, 0): 12
// You still have to multiply and store the results so I'd argue this is not a free lunch, but I can assume that the cost of the multiplication is lower; 
// although I can't say just how much cheaper or how to calculate such a cost.
// I also find it interesting that the counter is 5 for 'steps' larger than 2, e.g., n = 5, 6, 7, 8, 