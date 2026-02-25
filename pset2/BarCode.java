public class BarCode {
    // Data Fields
    private String myZipCode;
    private String myBarCode;

    // Constructor
    public BarCode(String input){
        // if lenght of input is 5, then it is a zip code, else it is a bar code
        if (input.length() == 5){
            this.myZipCode = input;
            this.myBarCode = encode(input);
        } else {
            this.myZipCode = decode(input);
            this.myBarCode = input;
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
    private String encode(String aZipCode) {
        return "";
    }

    private String decode(String aBarCode){
        return "";
    }

    private boolean isValidZipCode(String aZipCode){
        return false;
    }

    private boolean isValidBarCode(String aBarCode){
        return false;
    }

    private int getCheckDigit(int anInt) {
        return 0;
    }

    private String digitToCode(int aDigit) {
        return "";
    }
    
    private int codeToDigit(String aCode){
        return 0;
    }
}