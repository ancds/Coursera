import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class SAP {

  private final Digraph g;

  /**
   * constructor takes a digraph (not necessarily a DAG)
   */
  public SAP(Digraph g) {
    if (g == null) {
      throw new IllegalArgumentException();
    }

    Digraph digraph = new Digraph(g.V());
    for (int v = 0; v < digraph.V(); v++) {
      for (int w : g.adj(v)) {
        digraph.addEdge(v, w);
      }
    }

    this.g = digraph;
  }

  /**
   * length of shortest ancestral path between v and w; -1 if no such path.
   */
  public int length(int v, int w) {
    Set<Integer> groupA = new HashSet<>();
    groupA.add(v);

    Set<Integer> groupB = new HashSet<>();
    groupB.add(w);

    return length(groupA, groupB);

  }

  /**
   * length of shortest ancestral path between any vertex in v and any vertex in
   * w; -1 if no such path.
   */
  public int length(Iterable<Integer> groupA, Iterable<Integer> groupB) {

    validateVertices(groupA);
    validateVertices(groupB);

    int[] groupADistTo = new int[g.V()];
    int[] groupBDistTo = new int[g.V()];

    int ancestor = bfsTwoGroup(groupA, groupB, groupADistTo, groupBDistTo);
    if (ancestor == -1) {
      return -1;
    }

    return groupADistTo[ancestor] + groupBDistTo[ancestor];

  }

  /**
   * a common ancestor of v and w that participates in a shortest ancestral path;
   * -1 if no such path.
   */
  public int ancestor(int v, int w) {

    Set<Integer> groupA = new HashSet<>();
    groupA.add(v);

    Set<Integer> groupB = new HashSet<>();
    groupB.add(w);

    return ancestor(groupA, groupB);

  }

  /**
   * a common ancestor that participates in shortest ancestral path; -1 if no such
   * path.
   */
  public int ancestor(Iterable<Integer> groupA, Iterable<Integer> groupB) {

    validateVertices(groupA);
    validateVertices(groupB);

    int[] groupADistTo = new int[g.V()];
    int[] groupBDistTo = new int[g.V()];

    return bfsTwoGroup(groupA, groupB, groupADistTo, groupBDistTo);

  }

  private int bfsTwoGroup(Iterable<Integer> groupA, Iterable<Integer> groupB, int[] groupADistTo,
      int[] groupBDistTo) {

    boolean[] markedA = new boolean[g.V()];
    boolean[] markedB = new boolean[g.V()];

    multiSourcesBfs(groupA, markedA, groupADistTo);
    multiSourcesBfs(groupB, markedB, groupBDistTo);

    Queue<Integer> ancestors = new Queue<>();
    for (int i = 0; i < markedA.length; i++) {
      if (markedA[i] && markedB[i]) {
        ancestors.enqueue(i);
      }
    }

    int ancestor = -1;
    int sap = Integer.MAX_VALUE;
    for (int v : ancestors) {
      int ap = groupADistTo[v] + groupBDistTo[v];
      if (ap < sap) {
        ancestor = v;
        sap = ap;
      }
    }

    return ancestor;

  }

  private void multiSourcesBfs(Iterable<Integer> sources, boolean[] marked, int[] distTo) {

    Queue<Integer> queue = new Queue<>();

    for (int v : sources) {
      marked[v] = true;
      queue.enqueue(v);
    }

    while (!queue.isEmpty()) {

      int v = queue.dequeue();

      for (int w : g.adj(v)) {

        if (!marked[w]) {

          marked[w] = true;
          distTo[w] = distTo[v] + 1;
          queue.enqueue(w);

        }

      }
    }
  }

  // throw an IllegalArgumentException unless {@code 0 <= v < V}
  private void validateVertices(Iterable<Integer> vertices) {
    if (vertices == null) {
      throw new IllegalArgumentException("argument is null");
    }
    int length = g.V();
    for (Integer v : vertices) {
      if (v == null || v < 0 || v >= length) {
        throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (length - 1));
      }
    }
  }

  // do unit testing of this class
  public static void main(String[] args) {

    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}