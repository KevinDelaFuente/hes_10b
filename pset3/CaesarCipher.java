/*
 * CaesarCipher.java
 * 
 * This program encrypts and decrypts text files using the Caesar cipher algorithm.
 * Users can choose to encipher or decipher files with a specified shift value.
 * The program processes files line by line, preserving non-uppercase characters.
 */

import java.util.Scanner;
import java.io.*;
import java.lang.StringBuilder;

class CaesarCipher{
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Welcome to CaesarCipher");

        receiveInput(keyboard);
        keyboard.close();

    }

    // This method enciphers a string by shifting uppercase letters by the specified amount
    // Non-uppercase characters are copied as-is. Supports arbitrary shift values (positive/negative)
    public static String caesarEncipher (String input, int shift){
        StringBuilder result = new StringBuilder();
        char anchor = (shift > 0) ? 'A' : 'Z';  // Choose anchor based on shift direction
        for (int i = 0; i < input.length(); i++) {
            char letter = input.charAt(i);
            if (letter >= 'A' && letter <= 'Z'){
                // The idea is if shift is positive, the cycle is anchored at 'A' and has a lenght of 26, using mod to wrap around. 
                // If shift is negative, the cycle is anchored at 'Z' and also has a length of 26, using mod to wrap around.
                result.append((char) (anchor + ((letter - anchor + shift) % 26))); 
            } else result.append(letter);  // Copy non-uppercase, non-alphabetical as-is
        }
        return result.toString();
    }

    // This method deciphers a string by calling caesarEncipher with negative shift
    public static String caesarDecipher (String input, int shift){
        return caesarEncipher(input, -shift);
    }

    // This method handles user input and file processing for encipher/decipher operations
    public static void receiveInput(Scanner keyboard){
        int choice;
        int inputShift;
        String inputPath;
        String outputPath;
        while (true){
            try{
                System.out.print("Enter 1 to encipher, or 2 to decipher (-1 to exit): ");
                choice = Integer.parseInt(keyboard.nextLine()); // parsing Int to avoid leaving an empty string
                if (choice != 1 && choice != 2 && choice != -1) {
                    System.out.println("Invalid choice. Please enter 1, 2, or -1.");
                    continue;
                }
                else if (choice == -1){
                    System.out.println("Goodbye!");
                    break;
                }
                else {
                    // Get shift value and file paths from user
                    System.out.println("What shift should I use? ");
                    inputShift = Integer.parseInt(keyboard.nextLine()); // parsing Int to avoid leaving an empty string
                    System.out.println("What is the input file name? ");
                    inputPath = keyboard.nextLine();
                    System.out.println("What is the output file name? ");
                    outputPath = keyboard.nextLine();
                    
                    // Prevent overwriting the input file
                    if (inputPath.equals(outputPath)) {
                    System.out.println("Error: Input and output must be different files!");
                    continue;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                continue;
            }
            
            // Process the file line by line using try with resources
            try (Scanner fileScanner = new Scanner(new File(inputPath));
                    PrintWriter writer = new PrintWriter(new File(outputPath))) {
                
                // Read each line, transform it, and write to output
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String processedLine = (choice == 1) ? caesarEncipher(line, inputShift) : caesarDecipher(line, inputShift);
                    writer.println(processedLine);
                }
                System.out.println("File processed successfully.");
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}