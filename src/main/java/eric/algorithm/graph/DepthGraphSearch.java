package eric.algorithm.graph;

public class DepthGraphSearch extends GraphSearch {

  /**
   * initialize instance and find all paths starting from vertex v in Graph g.
   */
  public DepthGraphSearch(Graph g, int v) {
    super(g, v);
  }

  private void depthFirstSearch(Graph g, int v) {

    marked[v] = true;
    count++;

    for (int w : g.adj(v)) {
      if (!marked[w]) {
        edgeTo[w] = v;
        depthFirstSearch(g, w);
      }
    }

  }

  @Override
  protected void initialize(Graph g, int v) {
    depthFirstSearch(g, v);
  }

}
