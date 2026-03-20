import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class ExamAnalysis {
    public static void main (String [] args){
        String answers = getAnswers();
        // System.out.println(answers);
        
        ArrayList<String> examData = getExamData();
        // System.out.println(examData);
        
        printTable(doStudentAnalysis (answers, examData));
        



    }

    // This method prompts the user to input the correct answers for the exam questions and returns them as a string.
    public static String getAnswers (){
        System.out.println("I hope you are ready to begin ...");
        System.out.print("Please type the correct answers to the exam questions,\r\n" + 
                        " one right after the other: ");
        Scanner keyboard =  new Scanner(System.in);
        String answers = keyboard.nextLine();
        // keyboard.close();ABCEDBACED
        return answers;
    }

    // This method prompts the user for a file path and attempts to read the file until a valid path is provided. 
    // It then reads each line of the file and stores it in an ArrayList, which is returned at the end.
    public static ArrayList<String> getExamData (){
        // Get path from user and try to open file until they provide a valid path
        System.out.print("What is the name of the file containing each student's\r\n" + 
                        " responses to the 10 questions? ");
        Scanner keyboard =  new Scanner(System.in);
        Scanner reader = null;
        // File f = null;
        while (reader == null){
            try{
                File f = new File(keyboard.nextLine());
                reader  = new Scanner(f);
            }
            catch (FileNotFoundException e){
                System.out.println("The path provided was not valid. Please try again");
            }
        }
        keyboard.close();

        // Read each line of the file and add it to an ArrayList
        ArrayList<String> examData = new ArrayList<String>();
        while (reader.hasNextLine()){
            examData.add(reader.nextLine());
        }
        reader.close();
        return examData;
    }

    public static String[][] doStudentAnalysis (String answers, ArrayList<String> examData) {
        String [][] matrix = new String [examData.size()-1][4];
        for (int i = 0; i < examData.size()-1; i++) {
            matrix[i][0] = String.valueOf(i+1); // Student number
            // counters for options
            int correct = 0;
            int incorrect = 0;
            int blank = 0;
            for (int j = 0; j < 10; j++){
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

    public static void printTable(String[][] matrix) {
        String [] header = new String[] { "Stundent #", "Correct", "Incorrect", "Blank" };
        System.out.println("-----------------------------------------------------");
        
        // Print the header row with left alignment and specified widths
        System.out.printf("| %-10s | %-10s | %-10s | %-10s |%n", header[0], header [1], header[2], header [3]);
        
        // Print another separator line
        System.out.println("-----------------------------------------------------");

        // Print data rows using the same format specifier
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("| %-10s | %-10s | %-10s | %-10s |%n", matrix[i][0], matrix[i][1], matrix[i][2], matrix [i][3]);
        }
        
        // Print a final separator line
        System.out.println("-----------------------------------------------------");
    }
}    
