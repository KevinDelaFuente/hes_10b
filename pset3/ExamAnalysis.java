/*
 * ExamAnalysis.java
 * 
 * This program analyzes multiple-choice exam results by comparing student responses
 * to the correct answer key. It provides two analysis outputs:
 *   >> Student Analysis: showing correct, incorrect, and blank answers for each student
 *   >> Question Analysis: showing the distribution of responses for each question
 * 
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class ExamAnalysis {
    // Constant to be changed if number of exams questions changes, used for validation and loops
    private static final int EXAM_LENGTH = 10;

    public static void main (String [] args){
        // Create a single Scanner for all user input
        Scanner keyboard = new Scanner(System.in);
        
        // Get the correct answers and the exam data from the user
        String answers = getAnswers(keyboard);
        ArrayList<String> examData = getExamData(keyboard);
        
        keyboard.close();
        
        // Process the exam data and print the results in a formatted table
        printTable(doStudentAnalysis (answers, examData));

        // Question Analysis
        System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++\r\n" + 
                           "QUESTION ANALYSIS     (* marks the correct response)\r\n" +   
                           "+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        for (int i = 1; i <= EXAM_LENGTH; i++){
            printQuestionAnalysis(doQuestionAnalysis(answers, examData, i), i);
        }
    }

    // This method prompts the user to input the correct answers for the exam questions and returns them as a string.
    public static String getAnswers (Scanner keyboard){
        System.out.println("I hope you are ready to begin ...");
        String answers = "";
        Boolean valid = false;

        while (!valid) {
            try{
                System.out.print("Please type the correct answers to the exam questions,\r\n" + 
                                " one right after the other: ");
                answers = keyboard.nextLine().toUpperCase();
                if (answers.length() != EXAM_LENGTH || !answers.matches("^[A-E]+$")) throw new IllegalArgumentException();
                valid = true;
            }
            catch (IllegalArgumentException e){
                System.out.println("The format or number of answers provided was not correct. Please try again.");
            }
        }
        
        return answers;
    }

    // This method prompts the user for a file path and attempts to read the file until a valid path is provided. 
    // It then reads each line of the file and stores it in an ArrayList, which is returned at the end.
    public static ArrayList<String> getExamData (Scanner keyboard){
        // Get path from user and try to open file until they provide a valid path
        System.out.printf("What is the name of the file containing each student's\r\n" + 
                        " responses to the %d questions? ", EXAM_LENGTH);
        Scanner reader = null;
        while (reader == null){
            try{
                File f = new File(keyboard.nextLine());
                reader  = new Scanner(f);
            }
            catch (FileNotFoundException e){
                System.out.println("The path provided was not valid. Please try again");
            }
        }

        // Read each line of the file and add it to an ArrayList if valid
        ArrayList<String> examData = new ArrayList<String>();
        while (reader.hasNextLine()){
            String line = reader.nextLine();
            if( line.length() != EXAM_LENGTH || !line.matches("^[A-E ]+$")) {
                System.out.printf("\nThe format of answer was not correct. Please check the row: %s. \r\n" +
                                  "Each line should contain exactly %d responses: A, B, C, D, E, or blank. \r\n", line, EXAM_LENGTH);
                System.out.println("-----------------------------Skipping this line-------------------------\n");
            }
            else {
                examData.add(line.toUpperCase()); // Converting to upper case for resiliency
                System.out.printf("\nStudent #%d's responses: %s\n", examData.size(), line);
            }
        }

        System.out.println("We have reached \"end of file!\"");

        reader.close();
        return examData;
    }

    // This method takes the correct answers and the exam data as input and processes each student's 
    // responses to determine the number of correct, incorrect, and blank answers.
    public static String[][] doStudentAnalysis (String answers, ArrayList<String> examData) {
        String [][] matrix = new String [examData.size()][4];
        for (int i = 0; i < examData.size(); i++) {
            matrix[i][0] = String.valueOf(i+1); // Student number
            // counters for options
            int correct = 0;
            int incorrect = 0;
            int blank = 0;
            for (int j = 0; j < EXAM_LENGTH; j++){
                if (examData.get(i).charAt(j) == ' ') blank++;
                else if (examData.get(i).charAt(j) == answers.charAt(j)) correct++;
                else incorrect++;
            }
            matrix[i][1] = String.valueOf(correct);
            matrix[i][2] = String.valueOf(incorrect);
            matrix[i][3] = String.valueOf(blank);
        }
        return matrix;
    }

    // This method analyzes a specific question by counting how many students selected each answer choice.
    // It returns a 3x6 matrix containing: row 0 = answer choices with asterisk for correct answer,
    // rows 1 = count of students for each choice, row 2 = percentage of students for each choice.
    public static String[][] doQuestionAnalysis (String answers, ArrayList<String> examData, int questionNum) {
        String [][] matrix = new String [3][6];
        matrix[0] = new String[] {"A", "B", "C", "D", "E", "Blank"};
        // Modify headers with asterisks for correct answer
        switch (answers.charAt(questionNum-1)) {
            case 'A': matrix[0][0] = matrix[0][0] + "*"; break;
            case 'B': matrix[0][1] = matrix[0][1] + "*"; break;
            case 'C': matrix[0][2] = matrix[0][2] + "*"; break;
            case 'D': matrix[0][3] = matrix[0][3] + "*"; break;
            case 'E': matrix[0][4] = matrix[0][4] + "*"; break;
        }

        // Set up counters
        int A_count = 0;
        int B_count = 0;
        int C_count = 0;
        int D_count = 0;
        int E_count = 0;
        int blank_count = 0;
        int total = examData.size();
        // Calculate counters for response
        for (String student : examData) {
            char response = student.charAt(questionNum-1);
            switch (response) {
                case 'A': A_count++; break;
                case 'B': B_count++; break;
                case 'C': C_count++; break;
                case 'D': D_count++; break;
                case 'E': E_count++; break;
                case ' ': blank_count++; break;
            }
        }
        // Total values
        matrix[1][0] = String.valueOf(A_count);
        matrix[1][1] = String.valueOf(B_count);
        matrix[1][2] = String.valueOf(C_count);
        matrix[1][3] = String.valueOf(D_count);
        matrix[1][4] = String.valueOf(E_count);
        matrix[1][5] = String.valueOf(blank_count);

        // Calculate and format percentages
        matrix[2][0] = String.format("%.1f%%", 100.0 * A_count/total);
        matrix[2][1] = String.format("%.1f%%", 100.0 * B_count/total);
        matrix[2][2] = String.format("%.1f%%", 100.0 * C_count/total);
        matrix[2][3] = String.format("%.1f%%", 100.0 * D_count/total);
        matrix[2][4] = String.format("%.1f%%", 100.0 * E_count/total);
        matrix[2][5] = String.format("%.1f%%", 100.0 * blank_count/total);       
        
       
        return matrix;
    }

    // This method takes a 3x6 matrix from doQuestionAnalysis and prints a formatted display
    // showing the question number, answer choices, counts, and percentages for each response option.
    public static void printQuestionAnalysis(String [][] matrix, int questionNum){
        
        System.out.println("\nQuestion #" + questionNum);

        // Print the header row with left alignment and specified widths
        System.out.printf("\n %-10s  %-10s  %-10s  %-10s  %-10s  %-10s %n", matrix[0][0], matrix[0][1], matrix[0][2], matrix[0][3], matrix[0][4], matrix[0][5]);
        System.out.printf("\n %-10s  %-10s  %-10s  %-10s  %-10s  %-10s %n", matrix[1][0], matrix[1][1], matrix[1][2], matrix[1][3], matrix[1][4], matrix[1][5]);
        System.out.printf("\n %-10s  %-10s  %-10s  %-10s  %-10s  %-10s %n", matrix[2][0], matrix[2][1], matrix[2][2], matrix[2][3], matrix[2][4], matrix[2][5]);


    }

    // This method takes a 2D array of strings (matrix) from doStudentAnalysis as input and prints it in a formatted table.
    public static void printTable(String[][] matrix) {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++\r\n" + 
                           "                   STUDENT ANALYSIS                  \r\n" +   
                           "+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        String[] header = new String[] { "Student #", "Correct", "Incorrect", "Blank" };
        System.out.println("-----------------------------------------------------");
        
        // Print the header row 
        System.out.printf("| %-10s | %-10s | %-10s | %-10s |%n", header[0], header[1], header[2], header[3]);
        System.out.println("-----------------------------------------------------");

        // Print data rows
        for (String[] row : matrix) {
            System.out.printf("| %-10s | %-10s | %-10s | %-10s |%n", row[0], row[1], row[2], row[3]);
        }
        System.out.println("-----------------------------------------------------");
    }
}    
