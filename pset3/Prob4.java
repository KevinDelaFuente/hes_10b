/*
 * Prob4.java
 * 
 * This program demonstrates how to handle exceptions when performing division of two integers.
 * 
 */

import java.util.Scanner;
import java.util.InputMismatchException;

class Prob4 {
    public static void main(String [] args){
        Scanner keyboard = new Scanner (System.in);
        int n1, n2;
        Integer r = null; // There is probably a better way but I didn't want to create a different variable to control 
        // the loop and I can't set an int to null!
    
        while(r == null) {
            try{
                System.out.print("Type an int: ");
                n1 = keyboard.nextInt();
                System.out.print("Now type another int: ");
                n2 = keyboard.nextInt();
                r = n1 / n2;
            }
            catch (ArithmeticException ae){
                System.out.println("You can't divide by zero. Try again.");
                keyboard.nextLine(); //Clear the invalid input from the scanner buffer
            }
            catch (InputMismatchException ime){
                System.out.println("Please enter an int type. Try again.");
                keyboard.nextLine(); //Clear the invalid input from the scanner buffer
            }
        }
        keyboard.close();
        System.out.println("r = " + r);

    }
}