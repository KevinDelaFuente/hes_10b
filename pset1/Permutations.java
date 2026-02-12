// This program generates all permutations of a given string using recursion.
class Permutations {
    public static void main(String[] args) {
        System.out.println("Permutations of \"DOG\":");
        listPermutations("DOG");
        System.out.println();

        System.out.println("Permutations of \"ABCD\":");
        listPermutations("ABCD");
        System.out.println();

        System.out.println("Permutations of \"A\":");
        listPermutations("A");
        System.out.println();

        System.out.println("Permutations of empty string:");
        listPermutations("");
        System.out.println();
    }

    // Public method as required by the assignment, tbh this is just a wrapper for the recursive helper method 
    // that feeds the prefix and remaining string into the recursive calls
    public static void listPermutations(String s) {
        listPermutationsHelper("", s);   // The biggest thing with the helper is keeping track of what has been concatenated 
                                                // and being able to feed it into the next recursive call
    }

    // helper method with actual logic
    private static void listPermutationsHelper(String prefix, String s) {
        if (s.length() == 0) {
            System.out.println(prefix);
        } else {
            for (int i = 0; i < s.length(); i++) { // This was the hardest part to figure out, it's recursive for each of the options
                String newPrefix = prefix + s.charAt(i);
                String newRemaining = s.substring(0, i) + s.substring(i + 1); // Need to keep both sides of the string in the remainding
                // System.out.println(s.charAt(i)); // Debug statement
                // System.out.println("Prefix: " + newPrefix + ", Remaining: " + newRemaining); // Debug statement
                listPermutationsHelper(newPrefix, newRemaining);
            }
        }
    }
}