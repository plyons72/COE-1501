import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Edge> implements Iterable<Edge> {
    private Node<Edge> first;     // top of stack
    private int n;                // size of the stack

    // helper linked list class
    private static class Node<Edge> {
        private Edge item;
        private Node<Edge> next;
    }

    /**
     * Initializes an empty stack.
     */
    public Stack() {
        first = null;
        n = 0;
    }

    /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this stack.
     *
     * @return the number of items in this stack
     */
    public int size() {
        return n;
    }

    /**
     * Adds the item to this stack.
     *
     * @param  item the item to add
     */
    public void push(Edge item) {
        Node<Edge> oldfirst = first;
        first = new Node<Edge>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Edge pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Edge item = first.item;        // save item to return
        first = first.next;            // delete first node
        n--;
        return item;                   // return the saved item
    }


    /**
     * Returns (but does not remove) the item most recently added to this stack.
     *
     * @return the item most recently added to this stack
     * @throws NoSuchElementException if this stack is empty
     */
    public Edge peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in this stack in LIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Edge item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }


    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     *
     * @return an iterator to this stack that iterates through the items in LIFO order
     */
    public Iterator<Edge> iterator() {
        return new ListIterator<Edge>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Edge> implements Iterator<Edge> {
        private Node<Edge> current;

        public ListIterator(Node<Edge> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Edge next() {
            if (!hasNext()) throw new NoSuchElementException();
            Edge item = current.item;
            current = current.next;
            return item;
        }
    }
}
