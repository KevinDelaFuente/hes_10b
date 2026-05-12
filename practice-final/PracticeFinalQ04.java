// PracticeFinalQ04.java

/**
 * Practice Final question 4.
 *
 * @author  David Habermehl
 * @version Last modified 03_May_2019
 **/
import java.util.Stack;
import java.util.LinkedList;

class PracticeFinalQ04 {
    public static void main( String[] args ) {
        Stack<Integer> s1 = intsToStack( new int[]{ -12, 0, 1, 8, 8, 8 });
        System.out.printf( "\nisSorted( %s ) = %b\n", s1, isSorted_k( s1 ) );
        System.out.printf( "     s1 = %s\n", s1 );

        Stack<Integer> s2 = intsToStack( new int[]{ -9, 10, 43, 24, 97 });
        System.out.printf( "\nisSorted( %s ) = %b\n", s2, isSorted_k( s2 ) );
        System.out.printf( "     s2 = %s\n", s2 );

        System.out.println();
    }

    static Stack<Integer> intsToStack( int[] intArray ) {
        Stack<Integer> s = new Stack<Integer>();
        for ( int i : intArray ) { s.push( i ); }
        return s;
    }

    static boolean isSorted( Stack<Integer> s ) {
        LinkedList<Integer> q = new LinkedList<Integer>();
        int prev=0, next;
        boolean sIsSorted = true;

        // prev is top item on s
        if ( !s.isEmpty() ) {
            prev = s.pop();
            q.add( prev );
        }

        while ( !s.isEmpty() ) {
            // We're doing the equivalent of an s2q() as we process the stack
            next = s.pop();
            q.add( next );
            //System.out.printf( "prev = %d, next = %d, next > prev = %b\n", prev, next, next > prev );

            // s is sorted if the values don't increase as we pop items off the top of the stack
            if ( next > prev ) {
                sIsSorted = false; // values did increase, so s is not sorted
                s2q( s, q );       // finish the in-progress s2q()
                break;
            }
            prev = next;
        }

        // Restore s to its original state
        q2s( q, s );
        s2q( s, q );
        q2s( q, s );

        return sIsSorted;
    }

    public static boolean isSorted_k(Stack<Integer> s){
        if(s.size() < 2) return true;
        boolean result = true;
        LinkedList<Integer> q =  new LinkedList<Integer>();
        Integer high = s.pop();
        Integer low;
        q.add(high);
    
        while(!s.isEmpty()){
            
            
            low = s.pop();
            q.add(low);
            if (low > high){
                result = false;
            } else {
                high = low;
            }
        }

        q2s(q, s);
        s2q(s, q);
        q2s(q, s);
        return result;
    }
    // Transfers the entire contents of stack s to queue q
    public static void s2q (Stack<Integer> s, LinkedList<Integer> q) {
        while (!s.isEmpty()) { q.add(s.pop()); }
    }

    // Transfers the entire contents of queue q to stack s
    public static void q2s (LinkedList<Integer> q, Stack<Integer> s) {
        while (!q.isEmpty()) s.push(q.remove());
    }
}