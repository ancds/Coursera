package eric.algorithm.graph;

import edu.princeton.cs.algs4.Queue;

public class BreadthGraphSearch extends GraphSearch {

  /**
   * initialize instance and find all paths starting from vertex v in Graph g.
   */
  public BreadthGraphSearch(Graph g, int v) {
    super(g, v);
  }

  private void breadthFirstSearch(Graph g, int s) {

    marked[s] = true;

    Queue<Integer> queue = new Queue<>();
    queue.enqueue(s);

    while (!queue.isEmpty()) {

      int v = queue.dequeue();

      for (int w : g.adj(v)) {

        if (!marked[w]) {

          count++;
          marked[w] = true;
          edgeTo[w] = v;
          queue.enqueue(w);
        }

      }

    }

  }

  @Override
  protected void initialize(Graph g, int v) {
    breadthFirstSearch(g, v);
  }

}
