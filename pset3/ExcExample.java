// Java program demonstrating exception handling with try-catch-finally blocks
// Tests how program flow changes when exceptions are thrown and caught

class MyException extends Exception {}

public class ExcExample {
    // Main method tests exception handling by calling doRisky with command-line argument
    public static void main (String [] args){ 
        String test = args[0];
        System.out.print("t");
        try {
            doRisky (test);
        }
        catch (MyException e) {
            System.out.print("a");
        }
        finally {
            System.out.print("w");
            System.out.println("s");
        }
    }

    // This method throws MyException if the argument equals "Warren", otherwise completes normally
    static void doRisky (String arg) throws MyException {
        System.out.print("h");
        // System.out.println("Warren".equals(arg));
        if ("Warren".equals(arg)) throw new MyException();
        System.out.print("r");
        System.out.print("o");
    }
}
