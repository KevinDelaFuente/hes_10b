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
// You still have to multiply and store the results so I'd argue this is not free lunch, but I can assume that the cost of the multiplication is lower; 
// although I can't say just how much cheaper or how to calculate such a cost.