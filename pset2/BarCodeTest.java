class BarCodeTest {

    public static void main(String[] args) {
                // Test cases for encode
                String[] encodeTests = {"95014", "12345", "99999", "10000", "54321", "00000", "ABCDE", "12", "12a45"};
                String[] expectedEncodings = {
                    "|:|:: :|:|: ||::: :::|| :|::| :::|||", // 95014 (check digit 1)
                    "|:::||::|:|::||::|::|:|:|:||::|", // 12345 (check digit 5)
                    "||:|::|:|::|:|::|:|::|:|::||:::|", // 99999 (check digit 5)
                    "|::||::::|::::|::::|::::||:::||", // 10000 (check digit 9)
                    "|:|:|:|::|::||:::||::|:|:|:||:|", // 54321 (check digit 5)
                    "|||::::|::::|::::|::::|::::||:::|", // 00000 (check digit 0)
                    "", // ABCDE (invalid)
                    "", // 12 (invalid)
                    ""  // 12a45 (invalid)
                };
                System.out.println("\nTesting encode:");
                for (int i = 0; i < encodeTests.length; i++) {
                    String zip = encodeTests[i];
                    try {
                        String encoded = encode(zip);
                        System.out.printf("encode('%s') = %s (expected: %s)%n", zip, encoded, expectedEncodings[i]);
                    } catch (Exception e) {
                        System.out.printf("encode('%s') threw exception: %s\n", zip, e.getMessage());
                    }
                }
        // String zipCode1 = "12345";
        // String zipCode2 = "54321";
        // String zipCode3 = "00000";
        // String zipCode4 = "99999";

        // System.out.println(zipCode1 + " is valid: " + isValidZipCode(zipCode1));
        // System.out.println(zipCode2 + " is valid: " + isValidZipCode(zipCode2));
        // System.out.println(zipCode3 + " is valid: " + isValidZipCode(zipCode3));
        // System.out.println(zipCode4 + " is valid: " + isValidZipCode(zipCode4));

        // Test cases for getCheckDigit
        // int[] testNumbers = {95014, 12345, 99999, 10000, 54321, 0, 11111, 22222, 55555, 98765};
        // int[] expectedCheckDigits = {1, 5, 5, 9, 5, 0, 5, 0, 5, 5};
        // System.out.println("Testing getCheckDigit:");
        // for (int i = 0; i < testNumbers.length; i++) {
        //     int num = testNumbers[i];
        //     int expected = expectedCheckDigits[i];
        //     int actual = getCheckDigit(num);
        //     System.out.printf("getCheckDigit(%d) = %d (expected: %d)%n", num, actual, expected);
        // }
    }

    private static boolean isValidZipCode(String aZipCode){
        int number = Integer.parseInt(aZipCode);
        return ((number >= 1) & (number <= 99999) & (aZipCode.length() == 5));
    }

    private static String encode(String aZipCode) {
        if (isValidZipCode(aZipCode)){
            String encoding = "|";
            for (int i =0; i < aZipCode.length(); i++){
                encoding += digitToCode((int)aZipCode.charAt(i)-'0');
            }
            encoding += digitToCode(getCheckDigit(Integer.parseInt(aZipCode)))+"|";
            return encoding;  
        } else return "";
    }

    private static int getCheckDigit(int anInt) {
        int sum = 0;
        do {
            sum += anInt % 10;
            anInt /= 10;
        } while (anInt > 0);
        return (10 - (sum % 10))%10;
    }

    private static String digitToCode(int aDigit) {
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
}