package pset1;

public class RecTriangle {

    public static void main (String[] args)
    {
        printTriangle(4);
    }

    public static void printTriangle (int s){
        for (int i = 0; i < s; i++){
            System.out.print( "[]");
        }
        System.out.println ();
        
        if (s < 1) return;
        printTriangle (s-1);
        
    }      
    
}


