import java.util.*;

public class Bag<Edge> implements Iterable<Edge> {
    private Node<Edge> first;    // beginning of bag
    private int size;            // number of elements in bag

    // helper linked list class
    private static class Node<Edge> {
        private Edge values;
        private Node<Edge> next;

        public Node (Edge val) {
        	values = val;
        }

        public void setNextNode (Node<Edge> n) {
        	next = n;
        }

        public void setValues (Edge val) {
        	values = val;
        }

        public Node<Edge> getNextNode () {
        	return next;
        }

        public Edge getValues () {
        	return values;
        }
    }

    /**
     * Initializes an empty bag.
     */
    public Bag() {
        first = null;
        size = 0;
    }

    /**
     * Returns true if this bag is empty.
     *
     * @return {@code true} if this bag is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this bag.
     *
     * @return the number of items in this bag
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item to this bag.
     *
     * @param edge the edge to add to this bag
     */
    public void add(Edge edge) {
        Node<Edge> oldfirst = first;
        first = new Node<Edge>(edge);
        first.setValues(edge);
        first.setNextNode(oldfirst);
        size++;
    }

    public void remove (Edge edge) {
    	Node<Edge> currentNode = first;
    	while (currentNode != null) {
    		if (currentNode.getValues() != edge) {
    			currentNode = currentNode.getNextNode();
    		}
    	}
    	if (currentNode == edge) {
    		System.out.println("Equal.");
    		currentNode.setValues(first.getValues());
    		first = first.getNextNode();
    		size--;
    		System.out.println("Removed");
    	}
    }

    /**
     * Returns an iterator that iterates over the items in this bag in arbitrary order.
     *
     * @return an iterator that iterates over the items in this bag in arbitrary order
     */
    public Iterator<Edge> iterator()  {
        return new ListIterator<Edge>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Edge> implements Iterator<Edge> {
        private Node<Edge> current;

        public ListIterator(Node<Edge> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Edge next() {
            if (!hasNext()) throw new NoSuchElementException();
            Edge e = current.getValues();
            current = current.next;
            return e;
        }
	}
}
