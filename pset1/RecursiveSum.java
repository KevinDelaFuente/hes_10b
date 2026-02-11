// Reciprocal sum from 1 to 1/n with a few test cases for sumTo
class RecursiveSum {

    public static void main(String [] args){
        // Test cases for sumTo
        System.out.println("sumTo(0) = " + sumTo(0)); // 0.0
        System.out.println("sumTo(1) = " + sumTo(1)); // 1.0
        System.out.println("sumTo(2) = " + sumTo(2)); // 1.5
        System.out.println("sumTo(3) = " + sumTo(3)); // ~1.8
        System.out.println("sumTo(4) = " + sumTo(4)); // ~2.0
        System.out.println("sumTo(5) = " + sumTo(5)); // ~2.2
        System.out.println("sumTo(-12) = " + sumTo(-12)); // Exception
    }

    // Add the recriprocal recursively from 1 to 1/n
    public static double sumTo(int n){
        if(n==0) return 0.0;
        else if (n > 0 ) {
            return (1.0/n) + sumTo(n-1);
        } else throw new IllegalArgumentException("Value must be non-negative");
    }
}