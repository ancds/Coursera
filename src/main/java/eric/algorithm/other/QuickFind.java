package eric.algorithm.other;

public class QuickFind implements UnionFind {

  private int[] connectedComponentIds;

  /** initialize data structure. */
  public QuickFind(int n) {
    connectedComponentIds = new int[n];
    for (int x = 0; x < connectedComponentIds.length; x++) {
      connectedComponentIds[x] = x;
    }
  }

  @Override
  public boolean union(int i, int j) {

    if (connected(i, j)) {
      return false;
    }

    int idOfi = connectedComponentIds[i];
    int idOfj = connectedComponentIds[j];

    for (int x = 0; x < connectedComponentIds.length; x++) {
      if (connectedComponentIds[x] == idOfi) {
        connectedComponentIds[x] = idOfj;
      }
    }

    return true;
  }

  @Override
  public boolean connected(int i, int j) {

    return connectedComponentIds[i] == connectedComponentIds[j];
  }

}
