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
        if (isValidZipCode(aZipCode)){
            String encoding = "|";
            for (int i =0; i < aZipCode.length(); i++){
                encoding += digitToCode((int)aZipCode.charAt(i)-'0');
            }
            encoding += digitToCode(getCheckDigit(Integer.parseInt(aZipCode)))+"|";
            return encoding;  
        } else return "";
    }

    private String decode(String aBarCode){
        return "";
    }

    private boolean isValidZipCode(String aZipCode){
        int number = Integer.parseInt(aZipCode);
        return ((number >= 1) & (number <= 99999) & (aZipCode.length() == 5));
    }

    private boolean isValidBarCode(String aBarCode){
        return false;
    }

    private int getCheckDigit(int anInt) {
        int sum = 0;
        do {
            sum += anInt % 10;
            anInt /= 10;
        } while (anInt > 0);
        return (10 - (sum % 10))%10;
    }

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
    
    private int codeToDigit(String aCode){
        return 0;
    }
}