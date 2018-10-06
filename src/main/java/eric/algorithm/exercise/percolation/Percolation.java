package eric.algorithm.exercise.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  // let 0 = not opened, 1 = opened, 2 = opened and connected to bottom
  private byte[][] sites;
  private int numberOfOpenSites;
  private final WeightedQuickUnionUF unionFind;
  private final int size;
  private final int arraySize;
  private final int virtualTop;

  /**
   * initialization.
   * 
   * @param n size of grid.
   */
  public Percolation(int n) {

    if (n <= 0) {
      throw new IllegalArgumentException("n has to be > 0");
    }

    size = n;
    arraySize = n + 1;

    sites = new byte[arraySize][arraySize];
    numberOfOpenSites = 0;
    unionFind = new WeightedQuickUnionUF(arraySize * arraySize);
    virtualTop = 0;

  }

  /**
   * open.
   */
  public void open(int row, int col) {

    boundaryCheck(row, col);

    if (sites[row][col] != 0) {
      return;
    }

    numberOfOpenSites++;

    if (row == size) {
      sites[row][col] = 2;
    } else {
      sites[row][col] = 1;
    }

    if (row == 1) {
      update(0, 0, row, col);
    }

    if (row > 1 && sites[row - 1][col] != 0) {
      update(row, col, row - 1, col);
    }

    if (row < size && sites[row + 1][col] != 0) {
      update(row, col, row + 1, col);
    }

    if (col > 1 && sites[row][col - 1] != 0) {
      update(row, col, row, col - 1);
    }

    if (col < size && sites[row][col + 1] != 0) {
      update(row, col, row, col + 1);
    }

  }

  /**
   * isOpen.
   * 
   * @param row row
   * @param col col
   * @return ipOpen
   */
  public boolean isOpen(int row, int col) {

    boundaryCheck(row, col);

    return sites[row][col] != 0;
  }

  /**
   * is site (row, col) full?.
   * 
   * @param row row
   * @param col col
   * @return
   */
  public boolean isFull(int row, int col) {

    boundaryCheck(row, col);

    return unionFind.connected(virtualTop, getUfIndex(row, col));
  }

  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }

  /**
   * percolates.
   * 
   * @return if percolates.
   */
  public boolean percolates() {

    int root = unionFind.find(0);
    return sites[root / arraySize][root % arraySize] == 2;
  }

  private int getUfIndex(int row, int col) {
    return row * arraySize + col;
  }

  private void update(int node1Row, int node1Col, int node2Row, int node2Col) {

    int node1UfIndex = getUfIndex(node1Row, node1Col);
    int node2UfIndex = getUfIndex(node2Row, node2Col);

    int node1Root = unionFind.find(node1UfIndex);
    int node2Root = unionFind.find(node2UfIndex);

    unionFind.union(node1UfIndex, node2UfIndex);

    // if the one of the root is connected to bottom, the new root is connected to
    // bottom
    if (sites[node1Root / arraySize][node1Root % arraySize] == 2
        || sites[node2Root / arraySize][node2Root % arraySize] == 2) {
      int rootAfterUnion = unionFind.find(node1UfIndex);
      sites[rootAfterUnion / arraySize][rootAfterUnion % arraySize] = 2;
    }

  }

  private void boundaryCheck(int row, int col) {

    if (row < 1 || row > size || col < 1 || col > size) {

      String errorMsg = String.format("invalid input (%d, %d) for size %d*%d grid", row, col, size,
          size);
      throw new IllegalArgumentException(errorMsg);
    }
  }

  /**
   * test.
   * 
   * @param args empty
   */
  public static void main(String[] args) {

    int n = 10;

    Percolation percolation = new Percolation(n);

    while (!percolation.percolates()) {
      int row = StdRandom.uniform(1, n + 1);
      int col = StdRandom.uniform(1, n + 1);

      percolation.open(row, col);
    }

    StdOut.println("percolates with numberOfOpenSites: " + percolation.numberOfOpenSites());

  }
}
