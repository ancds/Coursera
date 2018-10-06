package eric.algorithm.exercise.puzzle;

import edu.princeton.cs.algs4.ResizingArrayQueue;

import java.util.Arrays;

public class Board {

  private final int[][] blocks;
  private final int n;

  /**
   * construct a board from an n-by-n array of blocks where blocks[i][j] = block
   * in row i, column j.
   * 
   * @param blocks blocks
   */
  public Board(int[][] blocks) {

    if (blocks == null) {
      throw new IllegalArgumentException();
    }

    n = blocks.length;
    this.blocks = new int[n][n];

    for (int i = 0; i < n; i++) {
      this.blocks[i] = Arrays.copyOf(blocks[i], n);
    }

  }

  /**
   * board dimension n.
   * 
   * @return board dimension n
   */
  public int dimension() {
    return n;

  }

  /**
   * number of blocks out of place.
   * 
   * @return number of blocks out of place
   */
  public int hamming() {

    int hamming = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {

        int currentNum = blocks[i][j];
        int goalNum = i * n + (j + 1);
        if (currentNum != goalNum && currentNum != 0) {
          hamming++;
        }

      }
    }

    return hamming;

  }

  /**
   * sum of Manhattan distances between blocks and goal.
   * 
   * @return sum of Manhattan distances between blocks and goal
   */
  public int manhattan() {

    int manhattan = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {

        int currentNum = blocks[i][j];
        if (currentNum == 0) {
          continue;
        }

        int goalPositionI = (currentNum - 1) / n;
        int goalPositionJ = (currentNum - 1) % n;

        manhattan += Math.abs(goalPositionI - i);
        manhattan += Math.abs(goalPositionJ - j);

      }
    }

    return manhattan;

  }

  /**
   * is this board the goal board?.
   * 
   * @return is this board the goal board?
   */
  public boolean isGoal() {
    return hamming() == 0;

  }

  /**
   * a board that is obtained by exchanging any pair of blocks.
   * 
   * @return a board that is obtained by exchanging any pair of blocks
   */
  public Board twin() {

    int[][] twin = new int[n][n];

    for (int i = 0; i < n; i++) {
      twin[i] = Arrays.copyOf(blocks[i], n);
    }

    if (twin[0][0] == 0 || twin[1][1] == 0) {

      exchange(twin, 0, 1, 1, 0);

    } else {

      exchange(twin, 0, 0, 1, 1);

    }

    return new Board(twin);

  }

  /**
   * does this board equal y?.
   */
  public boolean equals(Object y) {

    if (y == null) {
      return false;
    }

    if (!(y.getClass().equals(this.getClass()))) {
      return false;
    }

    Board that = (Board) y;
    if (this.n != that.n) {
      return false;
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (this.blocks[i][j] != that.blocks[i][j]) {
          return false;
        }
      }
    }

    return true;

  }

  @Override
  public int hashCode() {

    int hash = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        hash = blocks[i][j] * i + j;
      }
    }

    return hash;
  }

  /**
   * all neighboring boards.
   * 
   * @return all neighboring boards
   */
  public Iterable<Board> neighbors() {

    int blankPosI = 0;
    int blankPosJ = 0;

    outerloop: for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (blocks[i][j] == 0) {
          blankPosI = i;
          blankPosJ = j;
          break outerloop;
        }
      }
    }

    ResizingArrayQueue<Board> queue = new ResizingArrayQueue<>();
    if (blankPosI + 1 < n) {
      int[][] neighborBlocks = copyCurrentBlocks();
      exchange(neighborBlocks, blankPosI, blankPosJ, blankPosI + 1, blankPosJ);
      queue.enqueue(new Board(neighborBlocks));
    }

    if (blankPosI - 1 >= 0) {
      int[][] neighborBlocks = copyCurrentBlocks();
      exchange(neighborBlocks, blankPosI, blankPosJ, blankPosI - 1, blankPosJ);
      queue.enqueue(new Board(neighborBlocks));
    }

    if (blankPosJ + 1 < n) {
      int[][] neighborBlocks = copyCurrentBlocks();
      exchange(neighborBlocks, blankPosI, blankPosJ, blankPosI, blankPosJ + 1);
      queue.enqueue(new Board(neighborBlocks));
    }

    if (blankPosJ - 1 >= 0) {
      int[][] neighborBlocks = copyCurrentBlocks();
      exchange(neighborBlocks, blankPosI, blankPosJ, blankPosI, blankPosJ - 1);
      queue.enqueue(new Board(neighborBlocks));
    }

    return queue;

  }

  /**
   * string representation of this board.
   */
  public String toString() {

    int digit = n * n / 10 + 1;

    StringBuilder sb = new StringBuilder();
    sb.append(n);
    sb.append(System.lineSeparator());
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sb.append(String.format("%" + digit + "d", blocks[i][j]));
        sb.append(" ");
      }
      sb.append(System.lineSeparator());
    }

    return sb.toString();

  }

  private void exchange(int[][] b, int i1, int j1, int i2, int j2) {

    int temp = b[i1][j1];
    b[i1][j1] = b[i2][j2];
    b[i2][j2] = temp;

  }

  private int[][] copyCurrentBlocks() {

    int[][] b = new int[n][n];
    for (int i = 0; i < n; i++) {
      b[i] = Arrays.copyOf(blocks[i], n);
    }

    return b;
  }

}
