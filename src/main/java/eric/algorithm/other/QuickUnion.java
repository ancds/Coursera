package eric.algorithm.other;

public class QuickUnion implements UnionFind {

  private int[] nodeParentIds;
  private int[] componentSizes;

  /** initialize data structure. */
  public QuickUnion(int n) {
    nodeParentIds = new int[n];
    for (int x = 0; x < nodeParentIds.length; x++) {
      nodeParentIds[x] = x;
    }

    componentSizes = new int[n];
    for (int x = 0; x < componentSizes.length; x++) {
      componentSizes[x] = 1;
    }
  }

  @Override
  public boolean union(int i, int j) {

    int rootI = root(i);
    int rootJ = root(j);

    if (rootI == rootJ) {
      return false;
    }

    if (componentSizes[rootI] > componentSizes[rootJ]) {
      nodeParentIds[rootJ] = rootI;
      componentSizes[rootI] += componentSizes[rootJ];
    } else {
      nodeParentIds[rootI] = rootJ;
      componentSizes[rootJ] += componentSizes[rootI];
    }

    return true;
  }

  @Override
  public boolean connected(int i, int j) {

    return root(i) == root(j);
  }

  private int root(int i) {

    int node = i;
    while (node != nodeParentIds[node]) {
      nodeParentIds[node] = nodeParentIds[nodeParentIds[node]];
      node = nodeParentIds[node];
    }

    return node;
  }

}