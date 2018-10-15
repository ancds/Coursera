package eric.algorithm.graph;

import java.util.Iterator;

public class GraphProcess {

  /**
   * compute the degree of v.
   */
  public static int degree(Graph g, int v) {

    int degree = 0;
    Iterator<Integer> adjItr = g.adj(v).iterator();
    while (adjItr.hasNext()) {
      adjItr.next();
      degree++;
    }

    return degree;
  }

  /**
   * compute maximum degree.
   */
  public static int maxDegree(Graph g) {
    int max = 0;
    for (int i = 0; i < g.vertexNum(); i++) {
      int d = degree(g, i);
      if (d > max) {
        max = d;
      }
    }

    return max;
  }

  /**
   * compute average degree.
   */
  public static int avgDegree(Graph g) {

    return 2 * g.edgeNum() / g.vertexNum();
  }

  /**
   * count self-loops.
   */
  public static int numberOfSelfLoops(Graph g) {

    int selfLoops = 0;
    for (int i = 0; i < g.vertexNum(); i++) {

      for (Integer w : g.adj(i)) {
        if (w == i) {
          selfLoops++;
        }
      }

    }

    return selfLoops / 2; // each edge counted twice

  }

}
