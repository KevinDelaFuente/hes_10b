public class LinkedDequeueTest {

    public static void main(String[] args) {

        // constructor / isEmpty / size
        LinkedDequeue dq = new LinkedDequeue();
        System.out.println("isEmpty: " + dq.isEmpty() + "  (expected: true)");
        System.out.println("size: " + dq.size() + "  (expected: 0)");

        // tailAdd
        dq.tailAdd("A");
        dq.tailAdd("B");
        dq.tailAdd("C");
        System.out.println("tailAdd A,B,C: " + dq + "  (expected: A, B, C)");
        System.out.println("size: " + dq.size() + "  (expected: 3)");
        System.out.println("isEmpty: " + dq.isEmpty() + "  (expected: false)");

        // headAdd
        dq.headAdd("Z");
        System.out.println("headAdd Z: " + dq + "  (expected: Z, A, B, C)");
        System.out.println("size: " + dq.size() + "  (expected: 4)");

        // peek (should not change the dequeue)
        System.out.println("headPeek: " + dq.headPeek() + "  (expected: Z)");
        System.out.println("tailPeek: " + dq.tailPeek() + "  (expected: C)");
        System.out.println("size after peeks: " + dq.size() + "  (expected: 4)");

        // headRemove
        System.out.println("headRemove: " + dq.headRemove() + "  (expected: Z)");
        System.out.println("remaining: " + dq + "  (expected: A, B, C)");
        System.out.println("size: " + dq.size() + "  (expected: 3)");

        // tailRemove
        System.out.println("tailRemove: " + dq.tailRemove() + "  (expected: C)");
        System.out.println("remaining: " + dq + "  (expected: A, B)");
        System.out.println("size: " + dq.size() + "  (expected: 2)");

        // headAdd into an empty dequeue
        LinkedDequeue dq2 = new LinkedDequeue();
        dq2.headAdd("X");
        System.out.println("headAdd X into empty: " + dq2 + "  (expected: X)");
        System.out.println("headPeek: " + dq2.headPeek() + "  (expected: X)");
        System.out.println("tailPeek: " + dq2.tailPeek() + "  (expected: X)");
        System.out.println("size: " + dq2.size() + "  (expected: 1)");

        // alternate removes from both ends until empty
        LinkedDequeue dq3 = new LinkedDequeue();
        dq3.tailAdd(1); dq3.tailAdd(2); dq3.tailAdd(3); dq3.tailAdd(4);
        System.out.println("tailAdd 1,2,3,4: " + dq3 + "  (expected: 1, 2, 3, 4)");
        System.out.println("headRemove: " + dq3.headRemove() + "  (expected: 1)");
        System.out.println("tailRemove: " + dq3.tailRemove() + "  (expected: 4)");
        System.out.println("headRemove: " + dq3.headRemove() + "  (expected: 2)");
        System.out.println("tailRemove: " + dq3.tailRemove() + "  (expected: 3)");
        System.out.println("isEmpty: " + dq3.isEmpty() + "  (expected: true)");
        System.out.println("size: " + dq3.size() + "  (expected: 0)");

        // mixed types
        LinkedDequeue dq4 = new LinkedDequeue();
        dq4.tailAdd(10); dq4.headAdd(5); dq4.tailAdd(20);
        System.out.println("tailAdd 10, headAdd 5, tailAdd 20: " + dq4 + "  (expected: 5, 10, 20)");

        // extra credit: DequeueUnderflowException
        System.out.println("\nExtra credit -- DequeueUnderflowException:");
        LinkedDequeue empty = new LinkedDequeue();

        try {
            empty.headRemove();
        } catch (LinkedDequeue.DequeueUnderflowException e) {
            System.out.println("headRemove on empty: " + e.getMessage());
        }

        try {
            empty.tailRemove();
        } catch (LinkedDequeue.DequeueUnderflowException e) {
            System.out.println("tailRemove on empty: " + e.getMessage());
        }

        try {
            empty.headPeek();
        } catch (LinkedDequeue.DequeueUnderflowException e) {
            System.out.println("headPeek on empty: " + e.getMessage());
        }

        try {
            empty.tailPeek();
        } catch (LinkedDequeue.DequeueUnderflowException e) {
            System.out.println("tailPeek on empty: " + e.getMessage());
        }
    }
}
