enum Months { JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG,
 SEP, OCT, NOV, DEC} ;
// Class to determine number of days in a month, accounting for leap years
class Enumeration {

    public static int daysInMonth (Months m, int year) {
        switch (m) {
            case JAN: return 31;
            case FEB: 
                /* There are only 4 cases to evaluate because 4 * 100 = 400 so a truth table is easy..
                | year % 400 == 0 | year % 100 == 0 | year % 4 == 0 | Leap Year? |
                |-----------------|-----------------|---------------|------------|
                |       T         >       T         >      T        |     T      | 1) YES: If divisible by 400 it HAS to be divisible by 100 and 4 
                |       F         |       T         >      T        |     F      | 2) NO : If divisible by 100 it HAS to be divisible by 4
                |       F         |       F         |      T        |     T      | 3) YES
                |       F         |       F         |      F        |     F      | 4) NO 
                
                case 1 only requires checking year % 400 == 0
                case 3 does require the triple check
                */
                if ( (year%400!=0 && year%100!=0 && year%4==0) || (year%400==0) ) {
                    return 29;
                } else {return 28;}
            case MAR: return 31;
            case APR: return 30;
            case MAY: return 31;
            case JUN: return 30;
            case JUL: return 31;
            case AUG: return 31;
            case SEP: return 30;
            case OCT: return 31;
            case NOV: return 30;
            case DEC: return 31;
            
            default: return 0; // should never happen but just in case

        }
    }
    
    // Test for year 2400, which is a leap year
    public static void main(String[] args) {
        for (Months m : Months.values()){
            System.out.println (m + " 2400 has " + daysInMonth(m, 2400) + " days!");
        }
    }
}