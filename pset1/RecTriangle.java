// Class to print a recursive "[]" pattern
public class RecTriangle {

    public static void main (String[] args)
    {
        printTriangle(4);
    }

    public static void printTriangle (int s){
        // By printing first, the drill down of s happens after
        for (int i = 0; i < s; i++){
            System.out.print( "[]");
        }
        System.out.println ();
        
        if (s < 1) return;
        printTriangle (s-1);
        
    }      
    
}


