import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private Edge[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path

    /**
     * Computes the shortest path between the source vertex {@code s}
     * and every other vertex in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BFS(Graph G, int s, String flag) {
        marked = new boolean[G.getVertices()];
        distTo = new int[G.getVertices()];
        edgeTo = new Edge[G.getVertices()];
        //validateVertex(s);
        //bfs(G, s);

        if (flag.equalsIgnoreCase("Regular")) {
        	bfs(G, s);
        }
        if (flag.equalsIgnoreCase("Copper")) {
        	bfsCopperOnly(G, s);
        }
        if (flag.equalsIgnoreCase("Remove Two")) {
        	bfsRemainConnected(G, s);
        }
    }

    // breadth-first search from a single source
    private void bfs(Graph G, int s) {
        Queue<Edge> q = new LinkedList<Edge>();		// can't instantiate a Queue directly, instantiate a subclass (LL)
        for (int v = 0; v < G.getVertices(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;
        Edge e = new Edge(s, s, "", 0, 0);
        q.add(e);

        while (!q.isEmpty()) {
            int v = q.remove().to();
            for (Edge tempEdge : G.adj(v)) {
            	int w = tempEdge.to();
                if (!marked[w]) {
                    edgeTo[w] = tempEdge;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(tempEdge);
                }
            }
        }
    }

 // breadth-first search from a single source
    private void bfsCopperOnly(Graph G, int s) {
        Queue<Edge> q = new LinkedList<Edge>();		// can't instantiate a Queue directly, instantiate a subclass (LL)
        for (int v = 0; v < G.getVertices(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;
        Edge e = new Edge(s, s, "", 0, 0);
        q.add(e);

        while (!q.isEmpty()) {
            int v = q.remove().to();
            for (Edge tempEdge : G.adj(v)) {
            	int w = tempEdge.to();
                if (!marked[w]) {
                    edgeTo[w] = tempEdge;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(tempEdge);
                }
            }
        }
    }

 // breadth-first search from a single source
    private void bfsRemainConnected(Graph G, int s) {

    	boolean remainConnected = true;
        Queue<Edge> q = new LinkedList<Edge>();		// can't instantiate a Queue directly, instantiate a subclass (LL)
        for (int v = 0; v < G.getVertices(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;
        Edge e = new Edge(s, s, "", 0, 0);
        q.add(e);

        while (!q.isEmpty()) {
            int v = q.remove().to();
            for (Edge tempEdge : G.adj(v)) {
            	int w = tempEdge.to();
                if (!marked[w]) {
                    edgeTo[w] = tempEdge;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.add(tempEdge);
                }
            }
        }
    }

    /**
     * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns a shortest path between the source vertex {@code s} (or sources)
     * and {@code v}, or {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        Edge e;
        for (e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        path.push(e);
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
