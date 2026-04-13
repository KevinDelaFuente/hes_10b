// Implement your calculator math logic in this class.

// You MUST include these comments in CalcBackend.java (modifed with your actual char
// values, of course)

//@@         Operation        char passed to feedChar()
//@@         ---------        -------------------------
//@@         Clear            'C',
//@@         Square Root      '\u221A',
//@@         Multiplication   '*',
//@@         Division         '/',
//@@         Addition         '+',
//@@         Subtraction      '-';

// ... in order for me to know which characters your feedChar() method associates with
// different operations. That information is necessary for me to test your CalcBackend
// class.

// You MUST initialize the calculator's state in the zero-arg constructor. You MUST NOT
// change feedChar's and getDisplayVal's signature or functionality. You MUST NOT have
// ANY other non-private members of this class.

// I will test your CalcBackend class via an expanded version of CalcBackendTest.java,
// which will create an instance of CalcBackend with the zero-arg constructor, pass
// simulated button clicks via feedChar, and retrieve resulting display Strings with
// getDisplayVal.

// Note that when I test your CalcBackend class, I am ONLY using your feedChar and
// getDisplayVal methods. That means that my tests COMPLETELY bypass your Calculator
// class. That's because  your Calculator class is not supposed to have ANY involvement
// with your calculator's computations. Your Calculator class should ONLY layout the
// calculator's JFrame, attach listeners, feed button clicks to CalcBackend via feedChar
// and then update the calculator's display via getDisplayVal.


import java.util.Scanner;



// Manages calculator math logic via a state machine. GUI feeds button clicks
// through feedChar() and reads the display value via getDisplayVal().
public class CalcBackend {

    // Variables defining calculator's internal state

    private enum State {READY_FIRST, READY_SECOND, CONSTRUCTING_OPERAND_LEFT, CONSTRUCTING_OPERAND_RIGHT};
    private enum LastButton {DIGIT, DECIMAL_POINT, UNARY_OPERATOR, BINARY_OPERATOR, EQUALS, CLEAR};
    
    private State state; // State of calculator
    // private LastButton lastButton; // Last button type pressed for state transitions. Needed?            
    private double displayVal; // Always contains double value matching GUI's display
    private double first_operand; // Stores first operand for binary operations
    private char pendingOp; // Stores pending binary operation, if any
    private int decimalPlaces; // Keeps track of decimal places 

    // Zero-arg constructor initializes calculator's state
    public CalcBackend() {
        this.state = State.CONSTRUCTING_OPERAND_LEFT;
        // this.lastButton = LastButton.EQUALS;
        this.displayVal = 0.0;
        this.first_operand = 0.0;
        this.pendingOp = '\0'; 
        this.decimalPlaces = 0;
    }


    // +++++++++++++++++++++++++++++++++++++++
    // TRANSITION LOGIC
    // +++++++++++++++++++++++++++++++++++++++
    // State	                    Input	        Action
    // ----------------------      -----------     --------------------------------------------------
    // ANY                         C               clear(), reset all to initial state
    //
    // CONSTRUCTING_OPERAND_LEFT   digit           append digit (multiply by 10 + digit)
    // CONSTRUCTING_OPERAND_LEFT   .               reset decimalPlaces, → CONSTRUCTING_OPERAND_RIGHT
    // CONSTRUCTING_OPERAND_LEFT   binary op       save displayVal→first_operand, save op, → READY_SECOND
    // CONSTRUCTING_OPERAND_LEFT   unary op        apply op (sqrt) to displayVal, → READY_FIRST
    // CONSTRUCTING_OPERAND_LEFT   =               compute first_operand pendingOp displayVal, → READY_FIRST
    //
    // CONSTRUCTING_OPERAND_RIGHT  digit           append decimal digit (increment decimalPlaces)
    // CONSTRUCTING_OPERAND_RIGHT  binary op       save displayVal→first_operand, save op, → READY_SECOND
    // CONSTRUCTING_OPERAND_RIGHT  unary op        apply op (sqrt) to displayVal, → READY_FIRST
    // CONSTRUCTING_OPERAND_RIGHT  =               compute first_operand pendingOp displayVal, → READY_FIRST
    //
    // READY_FIRST                 digit           reset first_operand & decimalPlaces, set displayVal, → CONSTRUCTING_OPERAND_LEFT
    // READY_FIRST                 .               reset first_operand, displayVal=0, → CONSTRUCTING_OPERAND_RIGHT
    // READY_FIRST                 unary op        apply op to displayVal (e.g. √), stay in READY_FIRST
    // READY_FIRST                 binary op       save displayVal→first_operand, save op, → READY_SECOND
    // READY_FIRST                 =               compute first_operand pendingOp displayVal, → READY_FIRST
    //
    // READY_SECOND                digit           set displayVal to digit, reset decimalPlaces, → CONSTRUCTING_OPERAND_LEFT
    // READY_SECOND                .               set displayVal=0, reset decimalPlaces, → CONSTRUCTING_OPERAND_RIGHT
    // READY_SECOND                unary op        apply op to first_operand display, → READY_SECOND (stay)
    // READY_SECOND                binary op       change pendingOp to new operator (stay in READY_SECOND)
    // READY_SECOND                =               stay put (no second operand entered yet)

    // Routes each button press to the correct handler based on current state.
    public void feedChar(char c) {
        if(inferLastButton(c) == LastButton.CLEAR) {
            clear();
        } else if(state == State.CONSTRUCTING_OPERAND_LEFT){
            switch (inferLastButton(c)) {
                case DIGIT -> constructingLeft_x_Digit(c);
                case DECIMAL_POINT -> constructingLeft_x_DecimalPoint(c);
                case UNARY_OPERATOR -> constructingLeft_x_UnaryOperator(c);
                case BINARY_OPERATOR -> constructingLeft_x_BinaryOperator(c);
                case EQUALS -> constructingLeft_x_Equals();
                default -> { /* ignore */ }
            }
        } else if (state == State.CONSTRUCTING_OPERAND_RIGHT) {
            switch (inferLastButton(c)) {
                case DIGIT -> constructingRight_x_Digit(c);
                case UNARY_OPERATOR -> constructingRight_x_UnaryOperator(c);
                case BINARY_OPERATOR -> constructingRight_x_BinaryOperator(c);
                case EQUALS -> constructingRight_x_Equals();
                default -> { /* ignore */ } // ignoring case DECIMAL_POINT since it doesn't apply in this state
            }
        } else if (state == State.READY_FIRST) {
            switch (inferLastButton(c)) {
                case DIGIT -> readyFirst_x_Digit(c);
                case DECIMAL_POINT -> readyFirst_x_DecimalPoint(c);
                case UNARY_OPERATOR -> readyFirst_x_UnaryOperator(c);
                case BINARY_OPERATOR -> readyFirst_x_BinaryOperator(c);
                case EQUALS -> readyFirst_x_Equals();
                default -> { /* ignore */ }
            }
        } else if (state == State.READY_SECOND) {
            switch (inferLastButton(c)) {
                case DIGIT -> readySecond_x_Digit(c);
                case DECIMAL_POINT -> readySecond_x_DecimalPoint(c);
                case UNARY_OPERATOR -> readySecond_x_UnaryOperator(c);
                case BINARY_OPERATOR -> readySecond_x_BinaryOperator(c);
                case EQUALS -> readySecond_x_Equals();
                
                default -> { /* ignore */ }
            }
        }
        


    }

    public String getDisplayVal() {
        String displayString = "" + this.displayVal;
        
        // Adjust displayString as necessary, say to show multiple
        // trailing zeroes to the right of the decimal point, or to
        // limit the length of displayString.
        
        return displayString;
    }

    // +++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTING_OPERAND_LEFT state methods
    // +++++++++++++++++++++++++++++++++++++++
     
    // Appends digit to the left side of the current number.
    private void constructingLeft_x_Digit (char c) {
        if(displayVal == 0.0 ) {
            displayVal = Character.getNumericValue(c);
        } else {
            displayVal = displayVal * 10 + Character.getNumericValue(c);
        }
    }

    // Switches to decimal entry mode.
    private void constructingLeft_x_DecimalPoint (char c) {
        this.state = State.CONSTRUCTING_OPERAND_RIGHT;
        this.decimalPlaces = 0;
    }

    // Saves current value and operator; computes pending op first if one exists (chaining).
    private void constructingLeft_x_BinaryOperator (char c) {
        if(pendingOp == '\0') { // If there is no pending operation, just save the current value and operator
            this.first_operand = this.displayVal;
            this.pendingOp = c;
            this.state = State.READY_SECOND;
        } else { // this is more in case of chaining operations, I want to keep the binary structure so we just make it as if pressed '='
            constructingLeft_x_Equals();
            this.first_operand = this.displayVal;
            this.pendingOp = c;
            this.state = State.READY_SECOND;
        }
    }

    // Applies unary operator (sqrt) to displayVal.
    private void constructingLeft_x_UnaryOperator (char c) {
        if (c == '\u221A') {
            try {
                this.displayVal = Math.sqrt(this.displayVal);
            } catch (ArithmeticException e) {
                // Handle the exception, e.g., set displayVal to NaN or show an error message
                this.displayVal = Double.NaN;
            }
            this.state = State.READY_FIRST;
        }
    }

    // Computes first_operand pendingOp displayVal and stores result.
    private void constructingLeft_x_Equals () {
        double result = switch (pendingOp) {
            case '+' -> first_operand + displayVal;
            case '-' -> first_operand - displayVal;
            case '*' -> first_operand * displayVal;
            case '/' -> first_operand / displayVal;
            default -> displayVal; // No pending operation, just return current value
        };
        this.displayVal = result;
        this.first_operand = 0.0; // Clear first operand after computation
        this.pendingOp = '\0'; // Clear pending operation after computation
        this.state = State.READY_FIRST;
    }

    // +++++++++++++++++++++++++++++++++++++++
    // CONSTRUCTING_OPERAND_RIGHT state methods
    // +++++++++++++++++++++++++++++++++++++++

    // Appends digit to the right (decimal) side of the current number.
    private void constructingRight_x_Digit (char c) {
        decimalPlaces++;
        displayVal += (double) Character.getNumericValue(c) / Math.pow(10, decimalPlaces);
    }
    
    // Applies unary operator (sqrt) to displayVal.
    private void constructingRight_x_UnaryOperator (char c) {
        if (c == '\u221A') {
            try {
                this.displayVal = Math.sqrt(this.displayVal);
            } catch (ArithmeticException e) {
                // Handle the exception, e.g., set displayVal to NaN or show an error message
                this.displayVal = Double.NaN;
            }
            this.state = State.READY_FIRST;
        }
    }

    // Saves current value and operator; computes pending op first if one exists (chaining).
    private void constructingRight_x_BinaryOperator (char c) {
        if(pendingOp == '\0') { // If there is no pending operation, just save the current value and operator
            this.first_operand = this.displayVal;
            this.pendingOp = c;
            this.state = State.READY_SECOND;
        } else { // this is more in case of chaining operations, I want to keep the binary structure so we just make it as if pressed '='
            constructingRight_x_Equals();
            this.first_operand = this.displayVal;
            this.pendingOp = c;
            this.state = State.READY_SECOND;
        }
    }

    // Computes first_operand pendingOp displayVal and stores result.
    private void constructingRight_x_Equals () {
        double result = switch (pendingOp) {
            case '+' -> first_operand + displayVal;
            case '-' -> first_operand - displayVal;
            case '*' -> first_operand * displayVal;
            case '/' -> first_operand / displayVal;
            default -> displayVal; // No pending operation, just return current value
        };
        this.displayVal = result;
        this.first_operand = 0.0; // Clear first operand after computation
        this.pendingOp = '\0'; // Clear pending operation after computation
        this.state = State.READY_FIRST;
    }
    // +++++++++++++++++++++++++++++++++++++++
    // READY_FIRST state methods
    // +++++++++++++++++++++++++++++++++++++++

    // Resets and starts a new first operand from this digit.
    private void readyFirst_x_Digit (char c) {
        this.first_operand = 0.0;
        this.displayVal = Character.getNumericValue(c);
        this.state = State.CONSTRUCTING_OPERAND_LEFT;
        this.decimalPlaces = 0;
    }

    // Resets and begins a decimal-first operand (e.g. ".5").
    private void readyFirst_x_DecimalPoint (char c) {
        this.first_operand = 0.0;
        this.displayVal = 0.0;
        this.state = State.CONSTRUCTING_OPERAND_RIGHT;
        this.decimalPlaces = 0;
    }

    // Applies unary operator (sqrt) to displayVal; stays in READY_FIRST.
    private void readyFirst_x_UnaryOperator (char c) {
        if (c == '\u221A') {
            try {
                this.displayVal = Math.sqrt(this.displayVal);
            } catch (ArithmeticException e) {
                // Handle the exception, e.g., set displayVal to NaN or show an error message
                this.displayVal = Double.NaN;
            }
            // Stay in READY_FIRST since we can chain unary operations
        }
    }
    // Saves result as first operand and records the operator.
    private void readyFirst_x_BinaryOperator (char c){
        this.first_operand = this.displayVal;
        this.pendingOp = c;
        this.state = State.READY_SECOND;
    }
    // Re-evaluates using the stored operator (handles repeated '=').
    private void readyFirst_x_Equals () {
        constructingLeft_x_Equals();
    }


    // +++++++++++++++++++++++++++++++++++++++
    // READY_SECOND state methods
    // +++++++++++++++++++++++++++++++++++++++
    
    // Resets display to this digit and begins second operand entry.
    private void readySecond_x_Digit (char c) {
        this.displayVal = Character.getNumericValue(c);
        this.state = State.CONSTRUCTING_OPERAND_LEFT;
        this.decimalPlaces = 0;
    }

    // Resets display to 0 and begins decimal second operand entry.
    private void readySecond_x_DecimalPoint (char c) {
        this.displayVal = 0.0;
        this.state = State.CONSTRUCTING_OPERAND_RIGHT;
        this.decimalPlaces = 0;
    }

    // Applies unary operator (sqrt) to the displayed first operand.
    private void readySecond_x_UnaryOperator (char c) {
        if (c == '\u221A') {
            try {
                this.displayVal = Math.sqrt(this.displayVal);
            } catch (ArithmeticException e) {
                // Handle the exception, e.g., set displayVal to NaN or show an error message
                this.displayVal = Double.NaN;
            }
            this.state = State.READY_FIRST; // After applying unary operator, we go back to READY_FIRST
        }
    }
    // Replaces the pending operator without changing operands.
    private void readySecond_x_BinaryOperator (char c) {
        this.pendingOp = c;
    }

    // No second operand entered yet; nothing to compute.
    private void readySecond_x_Equals () {
        
    }

// ========================================
// UTILITY METHODS
// ========================================

    private void clear() {
        this.state = State.CONSTRUCTING_OPERAND_LEFT;
        this.displayVal = 0.0;
        this.first_operand = 0.0;
        this.pendingOp = '\0';
        this.decimalPlaces = 0;
    }

    private LastButton inferLastButton(char c) {
        if (Character.isDigit(c)) {
            return LastButton.DIGIT;
        } else if (c == '.') {
            return LastButton.DECIMAL_POINT;
        } else if (c == '=') {
            return LastButton.EQUALS;
        } else if (c == '+' || c == '-' || c == '*' || c == '/') {
            return LastButton.BINARY_OPERATOR;
        } else if (c == '\u221A') {
            return LastButton.UNARY_OPERATOR;
        } else if (c == 'C' || c == 'c') {
            return LastButton.CLEAR;
        } else {
            return null; // Invalid character
        }
        
    }
}


// This class is debugger for CalcBackend by simulating button presses via the console. 
// It will print the display value after each input.

// class backendTest {
//     public static void main (String [] args){
//         Scanner keyboard = new Scanner(System.in);
//         CalcBackend backend = new CalcBackend();
        
//         System.out.println("=== Calculator Backend Tester ===");
//         System.out.println("Enter characters one at a time (or 'q' to quit)");
//         System.out.println("Special: type 'sqrt' for √ symbol");
//         System.out.println();
//         System.out.println("Display: " + backend.getDisplayVal());
        
//         while (true) {
//             System.out.print("Input: ");
//             String input = keyboard.nextLine();
            
//             if (input.equals("q") || input.equals("quit")) {
//                 System.out.println("Exiting...");
//                 break;
//             }
            
//             char c;
//             if (input.equals("sqrt")) {
//                 c = '\u221A';
//             } else if (input.length() > 0) {
//                 c = input.charAt(0);
//             } else {
//                 continue;
//             }
            
//             backend.feedChar(c);
//             System.out.println("Display: " + backend.getDisplayVal());
//             System.out.println();
//         }
        
//         keyboard.close();
//     }
// }


    