/**
 * LinkedDequeue.java
 * A double-ended queue backed by a singly-linked list.
 * Supports add, peek, and remove at both head and tail.
 *
 * @author Kevin de la Fuente
 */
public class LinkedDequeue
{
    private QueueNode tail;
    private QueueNode head;
    private int count;

    // inner node class
    class QueueNode
    {
	    private Object item;
	    private QueueNode link;
    }

    // initializes an empty dequeue
    public LinkedDequeue ()
    {
	   tail = head = null;
	   count = 0;
    }

    // adds an element to the front
    public void headAdd (Object o){
      QueueNode temp = new QueueNode();
      temp.item = o;
      if(head == null){ 
         head = tail = temp;
         temp.link = null;
      }
      else { 
         temp.link = head;
         head = temp;
      }
      count++;
   }

   // returns the front element without removing it; throws DequeueUnderflowException if empty
   public Object headPeek(){
      if( head == null ) { throw new DequeueUnderflowException();}
      else {return head.item;}
   }

   // removes and returns the front element; throws DequeueUnderflowException if empty
   public Object headRemove(){
      Object temp;
      if (count == 0 ){
         throw new DequeueUnderflowException();
      } else { 
         temp = head.item;
         head = (count == 1) ? null : head.link;
         if (head == null) tail = null;
         count--;
         return temp;
      }
   }

   // adds an element to the rear
   public void tailAdd (Object o){
      QueueNode temp = new QueueNode();
      temp.item = o;
      temp.link = null;
      if( tail == null ){
         head = tail = temp;
      } else {
         tail.link = temp;
         tail = temp;
      }
      count++;
   }

   // returns the rear element without removing it; throws DequeueUnderflowException if empty
   public Object tailPeek(){
      if (tail == null ) throw new DequeueUnderflowException();
      else {return tail.item;}
   }

   // removes and returns the rear element; throws DequeueUnderflowException if empty
   public Object tailRemove(){
      Object temp;
      if ( count == 0 ){
         throw new DequeueUnderflowException();
      } else if (count == 1){
         temp = tail.item;
         tail = head = null;
         count--;
         return temp;
      } else {
         QueueNode current = head;
         while (current.link != tail){
            current = current.link;
         }
         temp = tail.item;
         current.link = null;
         tail = current;
         count--;
         return temp;
      }
   }

   // returns true if the dequeue has no elements
   public boolean isEmpty() {
      return count == 0;
   }
   // returns the number of elements in the dequeue
   public int size() { 
      return count;
   }

   // returns a comma-separated string of all elements from head to tail
   public String toString() { 
      StringBuilder sb = new StringBuilder();
      QueueNode current = head;
      while (current != null){
         sb.append(current.item.toString());
         if (current.link != null) sb.append(", ");
         current = current.link;
      }
      return sb.toString();
   }


   class DequeueUnderflowException extends RuntimeException
   {
      public DequeueUnderflowException()
      {
         super("DequeueUnderflowException: Dequeue is empty");
      }
   }

}