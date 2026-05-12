# Exam Study Notes - Common Mistakes

## Stack/Queue Implementation Mistakes

### 1. Interface vs. Implementation
❌ **Wrong:**
```java
Queue<Integer> q = new Queue<Integer>();  // Can't instantiate interface!
```
✅ **Correct:**
```java
Queue<Integer> q = new LinkedList<>();
// or
LinkedList<Integer> q = new LinkedList<>();
```
**Remember:** Queue is an interface. Use LinkedList, ArrayDeque, etc.

---

### 2. Stack Restoration - Order Matters!
When processing a stack and needing to restore it to original order:

❌ **Wrong:** Single transfer reverses the stack
```java
q2s(q, s);  // Only reverses once - stack is backwards!
```

✅ **Correct:** Three transfers restore original order
```java
q2s(q, s);   // Queue → Stack (reversed)
s2q(s, q);   // Stack → Queue (back to queue order)
q2s(q, s);   // Queue → Stack (original order restored!)
```

---

### 3. Early Return with Incomplete Restoration
❌ **Wrong:** Returning early corrupts the stack
```java
while (!s.isEmpty()) {
    item = s.pop();
    q.add(item);
    if (condition) {
        q2s(q, s);  // Only restores items popped SO FAR!
        return false;  // Original stack still has unpoped items → CORRUPTED
    }
}
```

✅ **Correct:** Use boolean flag, finish processing
```java
boolean result = true;
while (!s.isEmpty()) {
    item = s.pop();
    q.add(item);
    if (condition) {
        result = false;  // Flag it, but keep going
    }
}
// Now ALL items are in queue - safe to restore
q2s(q, s);
s2q(s, q);
q2s(q, s);
return result;
```

---

### 4. Empty Stack Check
❌ **Wrong:** Calling pop() without checking
```java
Integer item = s.pop();  // Crashes if empty!
```

✅ **Correct:** Check before popping
```java
if (s.isEmpty()) {
    return true;  // or handle appropriately
}
Integer item = s.pop();
```

---

### 5. Brace Placement - Control Flow
❌ **Wrong:** Code after loop is inside loop
```java
for (int i = 0; i < n; i++) {
    // loop body
    someCleanup();
    return true;    // These are INSIDE the loop!
}  // Loop closes here
// Missing return statement if loop doesn't execute!
```

✅ **Correct:** Proper brace alignment
```java
for (int i = 0; i < n; i++) {
    // loop body
}  // Loop closes here
someCleanup();  // Outside loop
return true;    // Outside loop
```

---

## Stack and Queue Methods Summary

### Stack<T> - Main Methods
**Stack is a class** (can be instantiated directly)

| Method | Description | Returns |
|--------|-------------|---------|
| `push(item)` | Add item to top of stack | item |
| `pop()` | Remove and return top item | T |
| `peek()` | View top item without removing | T |
| `isEmpty()` | Check if stack is empty | boolean |
| `size()` | Number of items in stack | int |

**Order:** LIFO (Last In, First Out)

---

### Queue<T> - Main Methods (Interface)
**Queue is an interface** - use `LinkedList<>()` or `ArrayDeque<>()`

| Method | Description | Returns |
|--------|-------------|---------|
| `add(item)` | Add item to end of queue | boolean |
| `remove()` | Remove and return front item | T |
| `peek()` | View front item without removing | T |
| `isEmpty()` | Check if queue is empty | boolean |
| `size()` | Number of items in queue | int |

**Order:** FIFO (First In, First Out)

---

### Helper Methods: s2q and q2s

#### Stack to Queue (s2q)
```java
public static void s2q(Stack<Integer> s, LinkedList<Integer> q) {
    while (!s.isEmpty()) { 
        q.add(s.pop()); 
    }
}
```
**Effect:** Empties stack into queue. **Reverses order!**
- Stack top → Queue end
- Original: Stack [bottom: 1, 2, 3 :top] → Queue [front: 3, 2, 1 :rear]

#### Queue to Stack (q2s)
```java
public static void q2s(LinkedList<Integer> q, Stack<Integer> s) {
    while (!q.isEmpty()) { 
        s.push(q.remove()); 
    }
}
```
**Effect:** Empties queue into stack. **Reverses order!**
- Queue front → Stack top
- Original: Queue [front: 1, 2, 3 :rear] → Stack [bottom: 3, 2, 1 :top]

#### Restoring Original Order
**To restore a stack after processing:**
```java
q2s(q, s);   // Transfer 1: Queue → Stack (reverses)
s2q(s, q);   // Transfer 2: Stack → Queue (reverses back)
q2s(q, s);   // Transfer 3: Queue → Stack (original order!)
```

**Example:**
1. Start: Stack `[1, 2, 3]` (3 on top)
2. After `s2q`: Queue `[3, 2, 1]` (3 at front)
3. After `q2s`: Stack `[3, 2, 1]` (1 on top) - **REVERSED!**
4. After `s2q`: Queue `[1, 2, 3]` (1 at front)
5. After `q2s`: Stack `[1, 2, 3]` (3 on top) - **RESTORED!**

---

## Key Takeaways
- **Interfaces can't be instantiated** - use concrete classes
- **Stack restoration requires 3 transfers** to preserve order
- **Never return early** when restoring data structures - finish processing first
- **Always check isEmpty()** before pop/remove operations
- **Watch your braces** - indentation can be misleading
