import java.util.Scanner;

/**
 * Palindrome.java
 * 
 * A program that determines whether a user-inputted string is a palindrome using recursive logic.
 */
class Palindrome {
    
    public static void main (String [] args) {
        // Test Cases:
        // String test1 = "racecar";
        // String test2 = "hello";
        // System.out.println (test1 + " is palindrome? " + isPalindrome(test1));
        // System.out.println (test2 + " is palindrome? " + isPalindrome(test2));
        String userString = userInput().toLowerCase();

        if (isPalindrome(userString)) {
            System.out.println(userString + " is a palindrome.");
        } else {
            System.out.println(userString + " is not a palindrome.");
        }
    }

    //  Recursively determines if a string is a palindrome.
    //  Skips non-alphanumeric characters (spaces, punctuation) using Character.isLetterOrDigit().
    //  assumes input has already been converted to lowercase in main or userInput.
    public static boolean isPalindrome (String s){
        int s_length = s.length();
        int left_index = 0;
        int right_index = s_length - 1;

        // In case of empty string or single character, defined as palindrome
        if (s_length <= 1)
            return true;
        else {
            // Skip non-alphanumeric characters from left
            while (left_index < s_length && !Character.isLetterOrDigit(s.charAt(left_index))) {
                left_index++;
            }
            // Same logic but in reverse
            while (right_index >= 0 && !Character.isLetterOrDigit(s.charAt(right_index))) {
                right_index--;
            }
            // If pointers crossed or met and it hasn't stopped before, it will be a palindrome. I thought it would be part of the base case
            // but sometimes I get substrings that are ' b' for example that are lenght 2 that don't fall in the base case but only have 
            // one letter to compare
            if (left_index >= right_index) {
                return true;
            }
            // Debugging statements:
            // System.out.println("Comparing " + s_lower.charAt(left_index) + " and " + s_lower.charAt(right_index));
            // System.out.println("Substring is: " + s.substring(left_index+1,right_index) + ", length: " + (right_index - left_index - 1));
            return (s.charAt(left_index) == (s.charAt(right_index)) && isPalindrome(s.substring(left_index+1,right_index)));
        }
    }

    // Get user input string
    public static String userInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a string to check if it's a palindrome:");
        String userString = sc.nextLine();
        sc.close();
        return userString;
    }

}