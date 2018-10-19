package eric.algorithm.graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

public class Graph {

  private final int vertices;
  private final Bag<Integer>[] edges;
  private int edgeNum;

  /**
   * create a V-vertex graph with no edges.
   * 
   * @param v number of vertices
   */
  @SuppressWarnings("unchecked")
  public Graph(int v) {

    this.vertices = v;
    edgeNum = 0;
    edges = (Bag<Integer>[]) new Bag[v];

    for (int i = 0; i < v; i++) {
      edges[i] = new Bag<Integer>();
    }
  }

  /**
   * create a V-vertex graph with Input.
   */
  public Graph(In in) {
    this(in.readInt()); // Read vertex and construct this graph.
    int e = in.readInt(); // Read edge.
    for (int i = 0; i < e; i++) { // Add an edge.
      int v = in.readInt(); // Read a vertex,
      int w = in.readInt(); // read another vertex,
      addEdge(v, w); // and add edge connecting them.
    }
  }

  public int vertexNum() {
    return vertices;
  }

  public int edgeNum() {
    return edgeNum;
  }

  /**
   * add edge v-w to this graph.
   */
  public void addEdge(int v, int w) {

    edges[v].add(w);
    edges[w].add(v);

    edgeNum++;

  }

  /**
   * vertices adjacent to v.
   */
  public Iterable<Integer> adj(int v) {

    return edges[v];
  }

  /**
   * string representation of the graph’s adjacency lists.
   */
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(vertices).append(" vertices, ").append(edgeNum).append(" edges\n");

    for (int v = 0; v < vertices; v++) {
      sb.append(v).append(": ");
      for (int w : this.adj(v)) {
        sb.append(w).append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
