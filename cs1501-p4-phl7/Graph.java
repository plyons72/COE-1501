import java.util.Collection;
import java.util.Iterator;

public class Graph {

	private final int V;
	private int E;
	private Bag<Edge>[] adj;

	public Graph(int V) {
		this.V = V;
		this.E = 0;

		adj = (Bag<Edge>[]) new Bag[V];
		for (int i = 0; i < V; i++)
			adj[i] = new Bag<Edge>();
	}

	public int getVertices () {
		return V;
	}

	public int getEdges () {
		return E;
	}

	private void checkVertex (int v) {
		if (v < 0 || v >= V) {
			throw new IllegalArgumentException(v + " isn't between 0 and " + (V - 1));
		}
	}

	public void addEdge (int startingV, int endingV, String type, int bandwidth, int length) {
		checkVertex(startingV);
		checkVertex(endingV);
		if (startingV != endingV) {
			Edge edgeOneToTwo = new Edge(startingV, endingV, type, bandwidth, length);
			Edge edgeTwoToOne = new Edge(endingV, startingV, type, bandwidth, length);
			adj[startingV].add(edgeOneToTwo);
			adj[endingV].add(edgeTwoToOne);
			E++;
		}
		System.out.println();
	}

    /**
     * Returns the edges incident on vertex {@code v}.
     *
     * @param  v the vertex
     * @return the edges incident on vertex {@code v} as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
	public Iterable<Edge> adj (int vertex) {
		checkVertex(vertex);
		return adj[vertex];
	}

	/**
     * Returns all edges in this edge-weighted graph.
     * To iterate over the edges in this edge-weighted graph, use foreach notation:
     * {@code for (Edge e : G.edges())}.
     *
     * @return all edges in this edge-weighted graph, as an iterable
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // only add one copy of each self loop (self loops will be consecutive)
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }

}
