// import java.util.Arrays;

class LowestGrade {
    public static void main(String [] args){
        int [] a = removeLowest ( 23, 90, 47, 55, 88);
        int [] b = removeLowest ( 85 );
        int [] c = removeLowest ();
        int [] d = removeLowest (59, 92, 93, 47, 88, 47);
        int [] e = removeLowest ( 100, 100, 100, 100);
        int [] f = removeLowest ( -10, 0, 10, -10, 20);
        System.out.println ("a = " + arrayPrint(a));
        System.out.println ("b = " + arrayPrint(b));
        System.out.println ("c = " + arrayPrint(c));
        System.out.println ("d = " + arrayPrint(d));
        System.out.println ("e = " + arrayPrint(e));
        System.out.println ("f = " + arrayPrint(f));
    }

    // removes the lowest grade from the array, if multiple lowest grades, removes the first one
    public static int [] removeLowest (int ... grades){
        if (grades.length <= 1) return grades; // if there are 0 or 1 grades, return the array as is
        else {
            int lowest = grades[0];
            int index = 0;
            for (int i = 0; i < grades.length; i++){
                if(grades[i] < lowest) {
                    lowest = grades[i];
                    index = i;
                }
                else continue;
            }
            return removeElement(grades, index);
        }
    }

    // returns an int [] with the element at index removed
    public static int[] removeElement(int [] arr, int index){
        int [] result = new int [arr.length - 1];
        // arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
        System.arraycopy(arr, 0, result, 0, index); // copying the elements before index
        System.arraycopy(arr, index+1, result, index, arr.length - index -1); // copying the elements after index
        return result;
    }

    // prints int[]
    public static String arrayPrint(int [] arr){
        String printedArray = "[";
        for (int value : arr) {
            printedArray += value + " ";
        }
        printedArray = printedArray.strip() + "]"; //removing the final " " from for loop and adding closing bracket
        return printedArray;
    }

}