
public class Edge {

    private int startingV;
    private int endingV;
    private String type;
    private int bandwidth;
    private int length;

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of
     * the given {@code weight}.
     *
     * @param  v one vertex
     * @param  w the other vertex
     * @param  weight the weight of this edge
     * @throws IllegalArgumentException if either {@code v} or {@code w}
     *         is a negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Edge(int startingV, int endingV, String type, int bandwidth, int length) {
        if (startingV < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        if (endingV < 0) throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        this.startingV = startingV;
        this.endingV = endingV;
        this.type = type;
        this.bandwidth = bandwidth;
        this.length = length;
    }

    public String type () {
    	return type;
    }

    public int bandwidth () {
    	return bandwidth;
    }

    public int length () {
    	return length;
    }

    public double latency () {
    	double time;
    	if (type().equalsIgnoreCase("optical")) {
    		time = length / 200000000;
    	}
    	else {
    		time = length / 230000000;
    	}
    	return time;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return startingV;
    }

    /**
     * Returns the endpoint of this edge that is different from the given vertex.
     *
     * @param  vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     *         endpoints of this edge
     */
    public int other(int vertex) {
        if      (vertex == startingV) return endingV;
        else if (vertex == endingV) return startingV;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    /**
     * Compares two edges by weight.
     * Note that {@code compareTo()} is not consistent with {@code equals()},
     * which uses the reference equality implementation inherited from {@code Object}.
     *
     * @param  that the other edge
     * @return a negative integer, zero, or positive integer depending on whether
     *         the weight of this is less than, equal to, or greater than the
     *         argument edge
     */
    /*@Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }*/

	/**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return startingV;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public int to() {
        return endingV;
    }

    /**
     * Returns the residual capacity of the edge in the direction
     *  to the given {@code vertex}.
     * @param vertex one endpoint of the edge
     * @return the residual capacity of the edge in the direction to the given vertex
     *   If {@code vertex} is the tail vertex, the residual capacity equals
     *   {@code capacity() - flow()}; if {@code vertex} is the head vertex, the
     *   residual capacity equals {@code flow()}.
     * @throws IllegalArgumentException if {@code vertex} is not one of the endpoints of the edge
     */
    /*public double residualCapacityTo(int vertex) {
        if      (vertex == startingV) return flow;              // backward edge
        else if (vertex == endingV) return capacity - flow;   // forward edge
        else throw new IllegalArgumentException("invalid endpoint");
    }*/

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format("%d-%d %s %d %d", startingV, endingV, type, bandwidth, length);
    }

}
