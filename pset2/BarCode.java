/**
 * BarCode represents a US postal bar code and provides methods to encode a zip code to a bar code,
 * decode a bar code to a zip code, and validate both formats. It throws exceptions for invalid input.
 */
public class BarCode {
    // Data Fields
    private String myZipCode;
    private String myBarCode;

    // Constructor
    public BarCode(String input){
        // if lenght of input is 5, then it is a zip code, else it is a bar code
        if (input.length() == 5){
            if (!isValidZipCode(input)) {
                throw new IllegalArgumentException("Illegal zip code value: " + input);
            } else{
            this.myZipCode = input;
            this.myBarCode = encode(input);
            }
        } else {
            if(!isValidBarCode(input)){
                throw new IllegalArgumentException("Illegal bar code value: " + input);
            } else{
            this.myZipCode = decode(input);
            this.myBarCode = input;
            }
        }
    }

    // Accessors
    public String getZipCode() {
        return this.myZipCode;
    }

    public String getBarCode() {
        return this.myBarCode;
    }

    // Methods
    // Encodes a zip code as a bar code string.
    private String encode(String aZipCode) {
        if (isValidZipCode(aZipCode)){
            String encoding = "|";
            for (int i =0; i < aZipCode.length(); i++){
                encoding += digitToCode((int)aZipCode.charAt(i)-'0');
            }
            encoding += digitToCode(getCheckDigit(Integer.parseInt(aZipCode)))+"|";
            return encoding;  
        } else return "";
    }

    // Decodes a bar code string to a zip code.
    private String decode(String aBarCode){
        String decoding = "";
        if (isValidBarCode(aBarCode)){
            for(int i = 1; i <= 5; i++){
                decoding += codeToDigit(aBarCode.substring((5*i)-4, (5*i)+1));
            }
        } return decoding;
    }

    // Checks if a zip code string is valid (5 digits, 1-99999).
    private boolean isValidZipCode(String aZipCode){
        if (!aZipCode.matches("[0-9]{5}")) {
            return false;
        }
        int number = Integer.parseInt(aZipCode);
        return (number >= 1 && number <= 99999);
    }

    // Checks if a bar code string is valid.
    private boolean isValidBarCode(String aBarCode){
        if (aBarCode == null || aBarCode.length() != 32) return false;
        if (aBarCode.charAt(0) != '|' || aBarCode.charAt(31) != '|') return false;
        for (int i = 0; i < aBarCode.length(); i++) {
            char c = aBarCode.charAt(i);
            if (c != '|' && c != ':') return false;
        }
        return true;
    }

    // Calculates the check digit for a zip code.
    private int getCheckDigit(int anInt) {
        int sum = 0;
        do {
            sum += anInt % 10;
            anInt /= 10;
        } while (anInt > 0);
        return (10 - (sum % 10))%10;
    }

    // Converts a digit to its bar code string representation.
    private String digitToCode(int aDigit) {
        return (
            switch (aDigit) {
                case 1 -> ":::||";
                case 2 -> "::|:|";
                case 3 -> "::||:";
                case 4 -> ":|::|";
                case 5 -> ":|:|:";
                case 6 -> ":||::";
                case 7 -> "|:::|";
                case 8 -> "|::|:";
                case 9 -> "|:|::";
                case 0 -> "||:::";
                default -> throw new IllegalStateException("Invalid input to digitToCode: " + aDigit);
            }
        );
    }
    
    // Converts a bar code string to its digit value.
    private int codeToDigit(String aCode){
        return (
            switch (aCode) {
                case ":::||" -> 1;
                case "::|:|" -> 2;
                case "::||:" -> 3;
                case ":|::|" -> 4;
                case ":|:|:" -> 5;
                case ":||::" -> 6;
                case "|:::|" -> 7;
                case "|::|:" -> 8;
                case "|:|::" -> 9;
                case "||:::" -> 0;
                default -> throw new IllegalStateException("Invalid input to codeToDigit: " + aCode);
            }
        );
    }
}