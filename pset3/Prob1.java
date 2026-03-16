// Java program named Prob1 that outputs all of its command-line arguments, one per line, in reverse order
class Prob1 {
    public static void main (String [] args){
        for (int i = args.length-1; i >= 0; i--){
            System.out.println(args[i]);
        }
    } 
}