package eric.algorithm.graph;

import edu.princeton.cs.algs4.Stack;

public abstract class GraphSearch {

  protected final int source;
  protected final boolean[] marked;
  protected final int[] edgeTo;
  protected int count;

  /**
   * initialize instance and find all paths starting from vertex v in Graph g.
   */
  public GraphSearch(Graph g, int v) {

    this.source = v;
    this.marked = new boolean[g.vertexNum()];
    this.edgeTo = new int[g.vertexNum()];

    initialize(g, v);
  }

  protected abstract void initialize(Graph g, int v);

  public boolean hasPathTo(int w) {
    return marked[w];
  }

  public int count() {
    return count;
  }

  /**
   * find path to vertex w from source.
   */
  public Iterable<Integer> pathTo(int w) {

    if (!hasPathTo(w)) {
      return null;
    }

    Stack<Integer> path = new Stack<>();
    for (int v = w; v != source; v = edgeTo[w]) {
      path.push(v);
    }
    path.push(source);

    return path;
  }

}
